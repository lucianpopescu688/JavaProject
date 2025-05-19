package model;

import java.time.LocalDateTime;

public class Ticket
{
    private int ticketID;
    private int performanceID;
    private String buyerName;
    private int nrOfSeats;
    private LocalDateTime saleDate;
    private int employeeID;

    public Ticket() { }

    public Ticket(int ticketID, int performance, String buyerName, int nrOfSeats, LocalDateTime saleDate, int employeeID)
    {
        this.ticketID = ticketID;
        this.performanceID = performance;
        this.buyerName = buyerName;
        this.nrOfSeats = nrOfSeats;
        this.saleDate = saleDate;
        this.employeeID = employeeID;
    }

    public int getTicketID()
    {
        return ticketID;
    }

    public void setTicketID(int ticketID)
    {
        this.ticketID = ticketID;
    }

    public int getPerformanceID()
    {
        return performanceID;
    }
    public void setPerformance(int performance)
    {
        this.performanceID = performance;
    }

    public String getBuyerName()
    {
        return buyerName;
    }

    public void setBuyerName(String buyerName)
    {
        this.buyerName = buyerName;
    }

    public int getNrOfSeats()
    {
        return nrOfSeats;
    }

    public void setNrOfSeats(int nrOfSeats)
    {
        this.nrOfSeats = nrOfSeats;
    }

    public LocalDateTime getSaleDate()
    {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate)
    {
        this.saleDate = saleDate;
    }

    public int getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(int employeeID)
    {
        this.employeeID = employeeID;
    }

    public String toString()
{
    return "Ticket ID: " + ticketID + " Performance: " + performanceID + " Buyer Name: " + buyerName + " Number of Seats: " + nrOfSeats + " Sale Date: " + saleDate + " by Employee: " + employeeID;
}
}
