package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.OfficeWorker;
import model.Performance;
import service.PerformanceService;
import service.TicketService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainWindow extends Stage {
    private final PerformanceService performanceService = new PerformanceService();
    private final TicketService ticketService = new TicketService();
    private final TableView<Performance> tableView = new TableView<>();
    private final ObservableList<Performance> performances = FXCollections.observableArrayList();

    public MainWindow(OfficeWorker worker) {
        initializeUI(worker);
        loadPerformances();
    }

    private void initializeUI(OfficeWorker worker) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f8f9fa;");

        // Top Panel - Search
        HBox searchPanel = new HBox(10);
        searchPanel.setPadding(new Insets(15));
        searchPanel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dee2e6; -fx-border-width: 0 0 1 0;");

        TextField searchField = new TextField();
        searchField.setPromptText("Enter date (YYYY-MM-DD)");
        searchField.setStyle("-fx-background-radius: 4; -fx-border-radius: 4; -fx-padding: 6;");
        searchField.setPrefWidth(200);

        Button searchButton = createStyledButton("Search", "#2196F3");
        searchButton.setOnAction(e -> searchPerformances(searchField.getText()));

        Button clearButton = createStyledButton("Clear", "#6c757d");
        clearButton.setOnAction(e -> loadPerformances());

        searchPanel.getChildren().addAll(
                createStyledLabel("Search Performances:"), searchField, searchButton, clearButton
        );
        root.setTop(searchPanel);

        // Center - Table
        setupTable();
        tableView.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; -fx-border-radius: 4;");
        root.setCenter(tableView);

        // Bottom Panel - Actions
        HBox actionPanel = new HBox(15);
        actionPanel.setPadding(new Insets(15));
        actionPanel.setAlignment(Pos.CENTER_RIGHT);

        Button sellButton = createStyledButton("Sell Tickets", "#2196F3");
        sellButton.setOnAction(e -> sellTickets(worker.getOfficeWorkerID()));

        Button logoutButton = createStyledButton("Logout", "#6c757d");
        logoutButton.setOnAction(e -> {
            new LoginWindow().show();
            close();
        });

        actionPanel.getChildren().addAll(sellButton, logoutButton);
        root.setBottom(actionPanel);

        Scene scene = new Scene(root, 1000, 600);
        setScene(scene);
        setTitle("Festival Ticket System - Main");
    }

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + color + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 4; " +
                "-fx-padding: 8 16;");

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: " + darkenColor(color) + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 4; " +
                        "-fx-padding: 8 16;"
        ));

        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 4; " +
                        "-fx-padding: 8 16;"
        ));

        return btn;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: #495057; -fx-font-weight: bold;");
        return label;
    }

    private String darkenColor(String hexColor) {
        return switch (hexColor) {
            case "#2196F3" -> "#1976D2";
            case "#6c757d" -> "#5a6268";
            default -> hexColor;
        };
    }

    private void setupTable() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Artist Column
        TableColumn<Performance, String> artistCol = new TableColumn<>("Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
        artistCol.setStyle("-fx-font-weight: bold;");

        // Date & Time Column
        TableColumn<Performance, LocalDateTime> dateCol = new TableColumn<>("Date & Time");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            @Override
            protected void updateItem(LocalDateTime date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(date.format(formatter));
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        // Place Column
        TableColumn<Performance, String> placeCol = new TableColumn<>("Place");
        placeCol.setCellValueFactory(new PropertyValueFactory<>("place"));

        // Available Tickets
        TableColumn<Performance, Integer> availableCol = new TableColumn<>("Available");
        availableCol.setCellValueFactory(new PropertyValueFactory<>("availableTickets"));
        availableCol.setStyle("-fx-alignment: CENTER;");

        // Sold Tickets
        TableColumn<Performance, Integer> soldCol = new TableColumn<>("Sold");
        soldCol.setCellValueFactory(new PropertyValueFactory<>("soldTickets"));
        soldCol.setStyle("-fx-alignment: CENTER;");

        // Sold-out highlighting
        tableView.setRowFactory(tv -> new TableRow<Performance>() {
            @Override
            protected void updateItem(Performance performance, boolean empty) {
                super.updateItem(performance, empty);
                if (performance == null || empty) {
                    setStyle("");
                } else if (performance.getAvailableTickets() <= 0) {
                    setStyle("-fx-background-color: #fff5f5;");
                } else {
                    setStyle("");
                }
            }
        });

        tableView.getColumns().addAll(artistCol, dateCol, placeCol, availableCol, soldCol);
    }

    private void loadPerformances() {
        performances.setAll(performanceService.getAllPerformances());
        tableView.setItems(performances);
    }

    private void searchPerformances(String dateString) {
        try {
            LocalDateTime date = LocalDateTime.parse(dateString + "T00:00",
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            performances.setAll(performanceService.getPerformancesByDate(date));
        } catch (Exception e) {
            showAlert("Error", "Invalid date format. Use YYYY-MM-DD");
        }
    }

    private void sellTickets(int employeeId) {
        Performance selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Warning", "No performance selected!");
            return;
        }

        if (selected.getAvailableTickets() <= 0) {
            showAlert("Error", "No tickets available for this performance!");
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Sell Tickets");
        dialog.getDialogPane().setStyle("-fx-background-color: white;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField buyerField = new TextField();
        buyerField.setPromptText("Buyer name");
        buyerField.setStyle("-fx-padding: 6; -fx-background-radius: 4;");

        TextField seatsField = new TextField();
        seatsField.setPromptText("Number of seats");
        seatsField.setStyle("-fx-padding: 6; -fx-background-radius: 4;");

        grid.add(createStyledLabel("Buyer Name:"), 0, 0);
        grid.add(buyerField, 1, 0);
        grid.add(createStyledLabel("Number of Seats:"), 0, 1);
        grid.add(seatsField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int seats = Integer.parseInt(seatsField.getText());
                    if (seats <= 0) throw new NumberFormatException();

                    boolean success = ticketService.sellTickets(
                            selected.getPerformanceID(),
                            buyerField.getText(),
                            seats,
                            employeeId
                    );

                    if (success) {
                        loadPerformances();
                        return "Success";
                    } else {
                        showAlert("Error", "Failed to sell tickets!");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid number of seats!");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.getDialogPane().setStyle("-fx-background-color: white;");
        alert.showAndWait();
    }
}