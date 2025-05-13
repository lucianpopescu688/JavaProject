package repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import model.Performance;

public interface IPerformanceRepository
{
    void insertPerformance(Performance performance);
    List<Performance> getAllPerformances();
    List<Performance> getPerformancesByDate(LocalDateTime date);
    void updatePerformance(Performance performance);
}
