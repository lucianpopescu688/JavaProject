package repository;

import model.RestPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RestPerformanceRepository extends JpaRepository<RestPerformance, Integer> {

    // Find performances by artist name
    @Query("SELECT p FROM RestPerformance p WHERE p.artist.artistName = :artistName")
    List<RestPerformance> findByArtistName(@Param("artistName") String artistName);

    // Find performances by place
    List<RestPerformance> findByPlace(String place);

    // Find performances between dates
    List<RestPerformance> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find performances with available tickets
    @Query("SELECT p FROM RestPerformance p WHERE p.availableTickets > 0")
    List<RestPerformance> findPerformancesWithAvailableTickets();
}