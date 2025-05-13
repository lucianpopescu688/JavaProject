package service;

import model.OfficeWorker;
import repository.IOfficeWorkerRepository;
import repository.OfficeWorkerRepository;

public class OfficeWorkerService {
    private final IOfficeWorkerRepository officeWorkerRepo;

    public OfficeWorkerService() {
        this.officeWorkerRepo = new OfficeWorkerRepository();
    }

    // Authenticate office worker (used during login)
    public OfficeWorker authenticate(String username, String password) {
        return officeWorkerRepo.authenticate(username, password);
    }

    // Add new office worker (for admin functionalities)
    public void addOfficeWorker(OfficeWorker worker) {
        officeWorkerRepo.addOfficeWorker(worker);
    }
}