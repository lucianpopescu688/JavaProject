package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.OfficeWorker;
import service.AuthenticationService;

public class LoginWindow extends Stage {
    private final AuthenticationService authService = new AuthenticationService();

    public LoginWindow() {
        initializeUI();
    }

    private void initializeUI() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(25));
        container.setStyle("-fx-background-color: #f0f4f8;");

        // Title
        Label title = new Label("Festival Ticket System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        // Username
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a4a4a;");
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 8;");
        usernameField.setPrefWidth(200);

        // Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #4a4a4a;");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 8;");
        passwordField.setPrefWidth(200);

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 5; -fx-padding: 10 20;");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Hover effect
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
                "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold; " +
                        "-fx-background-radius: 5; -fx-padding: 10 20;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; " +
                        "-fx-background-radius: 5; -fx-padding: 10 20;"));

        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        container.getChildren().addAll(title, grid, loginButton);

        Scene scene = new Scene(container, 400, 300);
        setScene(scene);
        setTitle("Festival Ticket System - Login");
    }

    private void handleLogin(String username, String password) {
        OfficeWorker worker = authService.authenticate(username, password);
        if (worker != null) {
            new MainWindow(worker).show(); // Open main window
            close(); // Close login window
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid credentials");
            alert.showAndWait();
        }
    }
}