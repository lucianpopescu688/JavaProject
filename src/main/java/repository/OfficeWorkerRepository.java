package repository;

import model.OfficeWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class OfficeWorkerRepository implements IOfficeWorkerRepository {
    private static final Logger logger = LoggerFactory.getLogger(OfficeWorkerRepository.class);
    private String connectionUrl;

    public OfficeWorkerRepository() {
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
    public void addOfficeWorker(OfficeWorker worker) {
        String sql = "INSERT INTO OfficeWorkers (Username, Password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, worker.getOfficeWorkerUsername());
            pstmt.setString(2, worker.getOfficeWorkerPassword());
            pstmt.executeUpdate();

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    worker.setOfficeWorkerID(rs.getInt(1));
                }
            }
            logger.info("Added office worker: {}", worker.getOfficeWorkerUsername());
        } catch (SQLException e) {
            logger.error("Failed to add office worker", e);
        }
    }

    @Override
    public OfficeWorker authenticate(String username, String password) {
        String sql = "SELECT * FROM OfficeWorkers WHERE Username = ? AND Password = ?";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new OfficeWorker(
                            rs.getInt("OfficeWorkerID"),
                            rs.getString("Username"),
                            rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Database error during authentication", e);
        }
        return null;
    }
}