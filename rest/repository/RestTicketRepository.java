package repository;

import model.RestTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RestTicketRepository extends JpaRepository<RestTicket, Integer> {

    // Find tickets by performance ID
    List<RestTicket> findByPerformancePerformanceID(int performanceID);

    // Find tickets by buyer name
    List<RestTicket> findByBuyerName(String buyerName);

    // Find tickets sold by specific employee
    List<RestTicket> findByEmployeeOfficeWorkerID(int employeeID);

    // Find tickets sold between dates
    List<RestTicket> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Get total seats sold for a performance
    @Query("SELECT SUM(t.nrOfSeats) FROM RestTicket t WHERE t.performance.performanceID = :performanceID")
    Integer getTotalSeatsSoldForPerformance(@Param("performanceID") int performanceID);
}