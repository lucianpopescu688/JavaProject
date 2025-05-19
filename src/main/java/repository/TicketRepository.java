package repository;

import model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;

public class TicketRepository implements ITicketRepository {
    private static final Logger logger = LoggerFactory.getLogger(TicketRepository.class);
    private String connectionUrl;

    public TicketRepository() {
        loadConfig();
    }

    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(input);
            connectionUrl = props.getProperty("db.url");
            Class.forName(props.getProperty("db.driver"));
        } catch (Exception e) {
            logger.error("Failed to load database config", e);
        }
    }

    @Override
    public void addTicket(Ticket ticket) {
        String insertSQL = "INSERT INTO Tickets (PerformanceID, BuyerName, NrOfSeats, SaleDate, EmployeeID) " +
                "VALUES (?, ?, ?, ?, ?)";
        String updateSQL = "UPDATE Performances SET AvailableTickets = AvailableTickets - ?, " +
                "SoldTickets = SoldTickets + ? WHERE PerformanceID = ?";

        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            conn.setAutoCommit(false);

            // Insert ticket
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, ticket.getPerformanceID());
                pstmt.setString(2, ticket.getBuyerName());
                pstmt.setInt(3, ticket.getNrOfSeats());
                pstmt.setTimestamp(4, Timestamp.valueOf(ticket.getSaleDate().withNano(0)));
                pstmt.setInt(5, ticket.getEmployeeID());
                pstmt.executeUpdate();
            }

            // Update performance
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setInt(1, ticket.getNrOfSeats());
                pstmt.setInt(2, ticket.getNrOfSeats());
                pstmt.setInt(3, ticket.getPerformanceID());
                pstmt.executeUpdate();
            }

            conn.commit();
            logger.info("Ticket added for {}", ticket.getBuyerName());
        } catch (SQLException e) {
            logger.error("Failed to add ticket", e);
        }
    }

    @Override
    public List<Ticket> getTicketsByPerformanceID(int performanceID) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Tickets WHERE PerformanceID = ?";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, performanceID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LocalDateTime saleDate = rs.getTimestamp("SaleDate").toLocalDateTime().withNano(0);
                    tickets.add(new Ticket(
                            rs.getInt("TicketID"),
                            rs.getInt("PerformanceID"),
                            rs.getString("BuyerName"),
                            rs.getInt("NrOfSeats"),
                            saleDate,
                            rs.getInt("EmployeeID")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch tickets for performance {}", performanceID, e);
        }
        return tickets;
    }

    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Tickets";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalDateTime saleDate = rs.getTimestamp("SaleDate").toLocalDateTime().withNano(0);
                tickets.add(new Ticket(
                        rs.getInt("TicketID"),
                        rs.getInt("PerformanceID"),
                        rs.getString("BuyerName"),
                        rs.getInt("NrOfSeats"),
                        saleDate,
                        rs.getInt("EmployeeID")
                ));
            }
            logger.info("Retrieved {} tickets", tickets.size());
        } catch (SQLException e) {
            logger.error("Failed to fetch all tickets", e);
        }
        return tickets;
    }
}