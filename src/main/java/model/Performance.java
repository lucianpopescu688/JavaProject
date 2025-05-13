package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Performance
{
    private int performanceID;
    private Artist artist;
    private LocalDateTime date;
    private String place;
    private int availableTickets;
    private int soldTickets;

    public Performance() {}

    public Performance(int performanceID, Artist artist, LocalDateTime date, String place, int availableTickets, int soldTickets)
    {
        this.performanceID = performanceID;
        this.artist = artist;
        this.date = date;
        this.place = place;
        this.availableTickets = availableTickets;
        this.soldTickets = soldTickets;
    }

    public int getPerformanceID()
    {
        return performanceID;
    }

    public void setPerformanceID(int performanceID)
    {
        this.performanceID = performanceID;
    }

    public Artist getArtist()
    {
        return artist;
    }

    public void setArtist(Artist artist)
    {
        this.artist = artist;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public int getAvailableTickets()
    {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets)
    {
        this.availableTickets = availableTickets;
    }

    public int getSoldTickets()
    {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets)
    {
        this.soldTickets = soldTickets;
    }

    public String toString()
    {
        return "Performance ID: " + performanceID + " Artist: " + artist + " Date: " + date + " Place: " + place + " Available Tickets: " + availableTickets + " Sold Tickets: " + soldTickets;
    }
}