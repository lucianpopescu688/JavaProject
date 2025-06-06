package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rest_tickets")
public class RestTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performance_id")
    private RestPerformance performance;

    private String buyerName;
    private int nrOfSeats;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime saleDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private RestOfficeWorker employee;

    public RestTicket() {}

    public RestTicket(int ticketID, RestPerformance performance, String buyerName, int nrOfSeats, LocalDateTime saleDate, RestOfficeWorker employee) {
        this.ticketID = ticketID;
        this.performance = performance;
        this.buyerName = buyerName;
        this.nrOfSeats = nrOfSeats;
        this.saleDate = saleDate;
        this.employee = employee;
    }

    // Converter from original Ticket model
    public RestTicket(model.Ticket originalTicket) {
        this.ticketID = originalTicket.getTicketID();
        // Note: You'll need to fetch the RestPerformance and RestOfficeWorker from their repositories
        this.buyerName = originalTicket.getBuyerName();
        this.nrOfSeats = originalTicket.getNrOfSeats();
        this.saleDate = originalTicket.getSaleDate();
    }

    // Convert to original Ticket model
    public model.Ticket toOriginalTicket() {
        return new model.Ticket(
                this.ticketID,
                this.performance != null ? this.performance.getPerformanceID() : 0,
                this.buyerName,
                this.nrOfSeats,
                this.saleDate,
                this.employee != null ? this.employee.getOfficeWorkerID() : 0
        );
    }

    // Getters and Setters
    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public RestPerformance getPerformance() {
        return performance;
    }

    public void setPerformance(RestPerformance performance) {
        this.performance = performance;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public int getNrOfSeats() {
        return nrOfSeats;
    }

    public void setNrOfSeats(int nrOfSeats) {
        this.nrOfSeats = nrOfSeats;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public RestOfficeWorker getEmployee() {
        return employee;
    }

    public void setEmployee(RestOfficeWorker employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "RestTicket ID: " + ticketID + " Performance: " + (performance != null ? performance.getPerformanceID() : "null") +
                " Buyer Name: " + buyerName + " Number of Seats: " + nrOfSeats + " Sale Date: " + saleDate +
                " by Employee: " + (employee != null ? employee.getOfficeWorkerID() : "null");
    }
}