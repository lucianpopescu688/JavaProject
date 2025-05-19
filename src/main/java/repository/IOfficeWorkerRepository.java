package repository;
import model.OfficeWorker;

public interface IOfficeWorkerRepository
{
    void addOfficeWorker(OfficeWorker officeWorker);
    OfficeWorker authenticate(String username, String password);
}