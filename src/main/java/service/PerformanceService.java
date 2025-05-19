package service;

import model.Artist;
import model.Performance;
import repository.IPerformanceRepository;
import repository.PerformanceRepository;
import java.time.LocalDateTime;
import java.util.List;

public class PerformanceService {
    private final IPerformanceRepository performanceRepo;
    private final ArtistService artistService;

    public PerformanceService() {
        this.performanceRepo = new PerformanceRepository();
        this.artistService = new ArtistService();
    }

    public List<Performance> getAllPerformances() {
        return performanceRepo.getAllPerformances();
    }

    public List<Performance> getPerformancesByDate(LocalDateTime date) {
        return performanceRepo.getPerformancesByDate(date);
    }

    public boolean createPerformance(Performance performance) {
        try {
            Artist artist = artistService.getArtistById(performance.getArtist().getArtistID());
            if (artist == null) return false;

            performance.setArtist(artist);
            performanceRepo.insertPerformance(performance);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sellTickets(int performanceId, String buyerName, int seats, int employeeId) {
        TicketService ticketService = new TicketService();
        return ticketService.sellTickets(performanceId, buyerName, seats, employeeId);
    }

    public boolean updatePerformance(Performance performance) {
        try {
            Artist artist = artistService.getArtistById(performance.getArtist().getArtistID());
            if (artist == null) return false;

            Performance existingPerf = performanceRepo.getPerformanceById(performance.getPerformanceID());
            if (existingPerf == null) return false;

            performance.setArtist(artist);
            performanceRepo.updatePerformance(performance);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePerformance(int performanceId) {
        try {
            Performance performance = performanceRepo.getPerformanceById(performanceId);
            if (performance == null) return false;

            performanceRepo.deletePerformance(performanceId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}