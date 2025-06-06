package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rest_office_workers")
public class RestOfficeWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int officeWorkerID;

    private String officeWorkerUsername;
    private String officeWorkerPassword;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RestTicket> tickets;

    public RestOfficeWorker() {}

    public RestOfficeWorker(int officeWorkerID, String officeWorkerUsername, String officeWorkerPassword) {
        this.officeWorkerID = officeWorkerID;
        this.officeWorkerUsername = officeWorkerUsername;
        this.officeWorkerPassword = officeWorkerPassword;
    }

    // Converter from original OfficeWorker model
    public RestOfficeWorker(model.OfficeWorker originalWorker) {
        this.officeWorkerID = originalWorker.getOfficeWorkerID();
        this.officeWorkerUsername = originalWorker.getOfficeWorkerUsername();
        this.officeWorkerPassword = originalWorker.getOfficeWorkerPassword();
    }

    // Convert to original OfficeWorker model
    public model.OfficeWorker toOriginalOfficeWorker() {
        return new model.OfficeWorker(this.officeWorkerID, this.officeWorkerUsername, this.officeWorkerPassword);
    }

    // Getters and Setters
    public int getOfficeWorkerID() {
        return officeWorkerID;
    }

    public void setOfficeWorkerID(int officeWorkerID) {
        this.officeWorkerID = officeWorkerID;
    }

    public String getOfficeWorkerUsername() {
        return officeWorkerUsername;
    }

    public void setOfficeWorkerUsername(String officeWorkerUsername) {
        this.officeWorkerUsername = officeWorkerUsername;
    }

    public String getOfficeWorkerPassword() {
        return officeWorkerPassword;
    }

    public void setOfficeWorkerPassword(String officeWorkerPassword) {
        this.officeWorkerPassword = officeWorkerPassword;
    }

    public List<RestTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<RestTicket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "RestOfficeWorker ID: " + officeWorkerID + " Username: " + officeWorkerUsername + " Password: " + officeWorkerPassword;
    }
}