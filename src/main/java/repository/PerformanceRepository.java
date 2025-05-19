package repository;

import model.Performance;
import model.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;

public class PerformanceRepository implements IPerformanceRepository {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceRepository.class);
    private String connectionUrl;
    private static final DateTimeFormatter DB_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public PerformanceRepository() {
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
    public void insertPerformance(Performance performance) {
        String sql = "INSERT INTO Performances (ArtistID, Date, Place, AvailableTickets, SoldTickets) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, performance.getArtist().getArtistID());
            pstmt.setString(2, performance.getDate().format(DB_DATE_FORMATTER));
            pstmt.setString(3, performance.getPlace());
            pstmt.setInt(4, performance.getAvailableTickets());
            pstmt.setInt(5, performance.getSoldTickets());
            pstmt.executeUpdate();

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    performance.setPerformanceID(rs.getInt(1));
                }
            }
            logger.info("Inserted performance with ID: {}", performance.getPerformanceID());
        } catch (SQLException e) {
            logger.error("Failed to insert performance", e);
        }
    }

    @Override
    public List<Performance> getAllPerformances() {
        List<Performance> performances = new ArrayList<>();
        String sql = "SELECT p.*, a.Name AS ArtistName FROM Performances p " +
                "INNER JOIN Artists a ON p.ArtistID = a.ArtistID";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Artist artist = new Artist(rs.getInt("ArtistID"), rs.getString("ArtistName"));
                LocalDateTime date = parseDatabaseDate(rs.getString("Date"));

                performances.add(new Performance(
                        rs.getInt("PerformanceID"),
                        artist,
                        date,
                        rs.getString("Place"),
                        rs.getInt("AvailableTickets"),
                        rs.getInt("SoldTickets")
                ));
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch performances", e);
        }
        return performances;
    }

    @Override
    public List<Performance> getPerformancesByDate(LocalDateTime date) {
        List<Performance> performances = new ArrayList<>();
        String sql = "SELECT p.*, a.Name AS ArtistName FROM Performances p " +
                "INNER JOIN Artists a ON p.ArtistID = a.ArtistID " +
                "WHERE strftime('%Y-%m-%d', Date) = strftime('%Y-%m-%d', ?)";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date.format(DB_DATE_FORMATTER));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Artist artist = new Artist(rs.getInt("ArtistID"), rs.getString("ArtistName"));
                    LocalDateTime perfDate = parseDatabaseDate(rs.getString("Date"));

                    performances.add(new Performance(
                            rs.getInt("PerformanceID"),
                            artist,
                            perfDate,
                            rs.getString("Place"),
                            rs.getInt("AvailableTickets"),
                            rs.getInt("SoldTickets")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch performances by date", e);
        }
        return performances;
    }

    private LocalDateTime parseDatabaseDate(String dateString) {
        String normalized = dateString.replace('T', ' ');
        return LocalDateTime.parse(normalized, DB_DATE_FORMATTER);
    }

    @Override
    public void updatePerformance(Performance performance) {
        String sql = "UPDATE Performances SET ArtistID = ?, Date = ?, Place = ?, " +
                "AvailableTickets = ?, SoldTickets = ? WHERE PerformanceID = ?";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, performance.getArtist().getArtistID());
            pstmt.setString(2, performance.getDate().format(DB_DATE_FORMATTER));
            pstmt.setString(3, performance.getPlace());
            pstmt.setInt(4, performance.getAvailableTickets());
            pstmt.setInt(5, performance.getSoldTickets());
            pstmt.setInt(6, performance.getPerformanceID());

            pstmt.executeUpdate();
            logger.info("Updated performance {}", performance.getPerformanceID());
        } catch (SQLException e) {
            logger.error("Failed to update performance", e);
        }
    }

    @Override
    public Performance getPerformanceById(int performanceId) {
        String sql = "SELECT p.*, a.Name AS ArtistName FROM Performances p " +
                "INNER JOIN Artists a ON p.ArtistID = a.ArtistID " +
                "WHERE p.PerformanceID = ?";

        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, performanceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Artist artist = new Artist(rs.getInt("ArtistID"), rs.getString("ArtistName"));
                    LocalDateTime date = parseDatabaseDate(rs.getString("Date"));

                    return new Performance(
                            rs.getInt("PerformanceID"),
                            artist,
                            date,
                            rs.getString("Place"),
                            rs.getInt("AvailableTickets"),
                            rs.getInt("SoldTickets")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch performance by ID: {}", performanceId, e);
        }
        return null;
    }

    @Override
    public void deletePerformance(int performanceId) {
        String sql = "DELETE FROM Performances WHERE PerformanceID = ?";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, performanceId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Deleted performance with ID: {}", performanceId);
            } else {
                logger.warn("No performance found with ID: {}", performanceId);
            }
        } catch (SQLException e) {
            logger.error("Failed to delete performance with ID: {}", performanceId, e);
        }
    }
}