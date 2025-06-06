package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rest_artists")
public class RestArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artistID;

    private String artistName;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RestPerformance> performances;

    public RestArtist() {}

    public RestArtist(int artistID, String artistName) {
        this.artistID = artistID;
        this.artistName = artistName;
    }

    // Converter from original Artist model
    public RestArtist(model.Artist originalArtist) {
        this.artistID = originalArtist.getArtistID();
        this.artistName = originalArtist.getArtistName();
    }

    // Convert to original Artist model
    public model.Artist toOriginalArtist() {
        return new model.Artist(this.artistID, this.artistName);
    }

    // Getters and Setters
    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<RestPerformance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<RestPerformance> performances) {
        this.performances = performances;
    }

    @Override
    public String toString() {
        return "RestArtist{" + "IDArtist=" + artistID + ", name=" + artistName + '}';
    }
}