package repository;

import model.RestOfficeWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RestOfficeWorkerRepository extends JpaRepository<RestOfficeWorker, Integer> {
    Optional<RestOfficeWorker> findByOfficeWorkerUsername(String username);
    Optional<RestOfficeWorker> findByOfficeWorkerUsernameAndOfficeWorkerPassword(String username, String password);
}