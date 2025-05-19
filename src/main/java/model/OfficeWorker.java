package model;

public class OfficeWorker {
    private int officeWorkerID;
    private String officeWorkerUsername;
    private String officeWorkerPassword;

    public OfficeWorker() {
    }

    public OfficeWorker(int officeWorkerID, String officeWorkerUsername, String officeWorkerPassword) {
        this.officeWorkerID = officeWorkerID;
        this.officeWorkerUsername = officeWorkerUsername;
        this.officeWorkerPassword = officeWorkerPassword;
    }

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

    public String toString() {
        return "Office Worker ID: " + officeWorkerID + " Office Worker Username: " + officeWorkerUsername + " Office Worker Password: " + officeWorkerPassword;
    }
}