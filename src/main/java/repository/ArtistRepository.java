package repository;

import model.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;

public class ArtistRepository implements IArtistRepository {
    private static final Logger logger = LoggerFactory.getLogger(ArtistRepository.class);
    private String connectionUrl;

    public ArtistRepository() {
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
    public void addArtist(Artist artist) {
        String sql = "INSERT INTO Artists (Name) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, artist.getArtistName());
            pstmt.executeUpdate();

            // Get last inserted ID using SQLite special function
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    artist.setArtistID(rs.getInt(1));
                    logger.info("Added artist with ID: {}", artist.getArtistID());
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to add artist", e);
        }
    }

    @Override
    public Artist getArtistByID(int artistId) {
        String sql = "SELECT * FROM Artists WHERE ArtistID = ?";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, artistId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Artist(rs.getInt("ArtistID"), rs.getString("Name"));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to fetch artist with ID: {}", artistId, e);
        }
        return null;
    }

    @Override
    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();
        String sql = "SELECT * FROM Artists";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                artists.add(new Artist(rs.getInt("ArtistID"), rs.getString("Name")));
            }
            logger.info("Retrieved {} artists", artists.size());
        } catch (SQLException e) {
            logger.error("Failed to fetch artists", e);
        }
        return artists;
    }
}