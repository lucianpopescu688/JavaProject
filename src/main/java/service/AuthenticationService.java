package service;

import model.OfficeWorker;

public class AuthenticationService {
    private final OfficeWorkerService officeWorkerService;

    public AuthenticationService() {
        this.officeWorkerService = new OfficeWorkerService();
    }

    // Authenticate user (wrapper for OfficeWorkerService)
    public OfficeWorker authenticate(String username, String password) {
        return officeWorkerService.authenticate(username, password);
    }
}