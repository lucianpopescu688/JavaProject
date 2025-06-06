package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rest_performances")
public class RestPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int performanceID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artist_id")
    private RestArtist artist;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private String place;
    private int availableTickets;
    private int soldTickets;

    public RestPerformance() {}

    public RestPerformance(int performanceID, RestArtist artist, LocalDateTime date, String place, int availableTickets, int soldTickets) {
        this.performanceID = performanceID;
        this.artist = artist;
        this.date = date;
        this.place = place;
        this.availableTickets = availableTickets;
        this.soldTickets = soldTickets;
    }

    // Converter from original Performance model
    public RestPerformance(model.Performance originalPerformance) {
        this.performanceID = originalPerformance.getPerformanceID();
        this.artist = new RestArtist(originalPerformance.getArtist());
        this.date = originalPerformance.getDate();
        this.place = originalPerformance.getPlace();
        this.availableTickets = originalPerformance.getAvailableTickets();
        this.soldTickets = originalPerformance.getSoldTickets();
    }

    // Convert to original Performance model
    public model.Performance toOriginalPerformance() {
        return new model.Performance(
                this.performanceID,
                this.artist.toOriginalArtist(),
                this.date,
                this.place,
                this.availableTickets,
                this.soldTickets
        );
    }

    // Getters and Setters
    public int getPerformanceID() {
        return performanceID;
    }

    public void setPerformanceID(int performanceID) {
        this.performanceID = performanceID;
    }

    public RestArtist getArtist() {
        return artist;
    }

    public void setArtist(RestArtist artist) {
        this.artist = artist;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }

    @Override
    public String toString() {
        return "RestPerformance ID: " + performanceID + " Artist: " + artist + " Date: " + date +
                " Place: " + place + " Available Tickets: " + availableTickets + " Sold Tickets: " + soldTickets;
    }
}