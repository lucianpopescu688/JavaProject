package controller;

import model.RestPerformance;
import rest.repository.RestPerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rest/performances")
@CrossOrigin(origins = "*")
public class RestPerformanceController {

    private static final Logger logger = LoggerFactory.getLogger(RestPerformanceController.class);

    @Autowired
    private RestPerformanceRepository performanceRepository;

    // GET all performances
    @GetMapping
    public ResponseEntity<List<RestPerformance>> getAllPerformances() {
        try {
            logger.info("Fetching all REST performances");
            List<RestPerformance> performances = performanceRepository.findAll();
            logger.info("Found {} REST performances", performances.size());
            return new ResponseEntity<>(performances, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all REST performances: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET performance by ID
    @GetMapping("/{id}")
    public ResponseEntity<RestPerformance> getPerformanceById(@PathVariable int id) {
        try {
            logger.info("Fetching REST performance with ID: {}", id);
            Optional<RestPerformance> performance = performanceRepository.findById(id);

            if (performance.isPresent()) {
                logger.info("REST Performance found: {}", performance.get());
                return new ResponseEntity<>(performance.get(), HttpStatus.OK);
            } else {
                logger.warn("REST Performance with ID {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching REST performance with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST - Create new performance
    @PostMapping
    public ResponseEntity<RestPerformance> createPerformance(@RequestBody RestPerformance performance) {
        try {
            logger.info("Creating new REST performance: {}", performance);
            performance.setPerformanceID(0); // Reset ID to let database generate it
            RestPerformance savedPerformance = performanceRepository.save(performance);
            logger.info("REST Performance created successfully with ID: {}", savedPerformance.getPerformanceID());
            return new ResponseEntity<>(savedPerformance, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating REST performance: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // PUT - Update existing performance
    @PutMapping("/{id}")
    public ResponseEntity<RestPerformance> updatePerformance(@PathVariable int id, @RequestBody RestPerformance performance) {
        try {
            logger.info("Updating REST performance with ID: {}", id);
            Optional<RestPerformance> existingPerformance = performanceRepository.findById(id);

            if (existingPerformance.isPresent()) {
                performance.setPerformanceID(id);
                RestPerformance updatedPerformance = performanceRepository.save(performance);
                logger.info("REST Performance updated successfully: {}", updatedPerformance);
                return new ResponseEntity<>(updatedPerformance, HttpStatus.OK);
            } else {
                logger.warn("REST Performance with ID {} not found for update", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating REST performance with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE performance by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerformance(@PathVariable int id) {
        try {
            logger.info("Deleting REST performance with ID: {}", id);
            Optional<RestPerformance> performance = performanceRepository.findById(id);

            if (performance.isPresent()) {
                performanceRepository.deleteById(id);
                logger.info("REST Performance with ID {} deleted successfully", id);
                return new ResponseEntity<>("REST Performance deleted successfully", HttpStatus.OK);
            } else {
                logger.warn("REST Performance with ID {} not found for deletion", id);
                return new ResponseEntity<>("REST Performance not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting REST performance with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>("Error deleting REST performance", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET performances by artist name
    @GetMapping("/artist/{artistName}")
    public ResponseEntity<List<RestPerformance>> getPerformancesByArtist(@PathVariable String artistName) {
        try {
            logger.info("Fetching REST performances for artist: {}", artistName);
            List<RestPerformance> performances = performanceRepository.findByArtistName(artistName);
            return new ResponseEntity<>(performances, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching REST performances for artist {}: {}", artistName, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET performances by place
    @GetMapping("/place/{place}")
    public ResponseEntity<List<RestPerformance>> getPerformancesByPlace(@PathVariable String place) {
        try {
            logger.info("Fetching REST performances at place: {}", place);
            List<RestPerformance> performances = performanceRepository.findByPlace(place);
            return new ResponseEntity<>(performances, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching REST performances at place {}: {}", place, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET performances with available tickets
    @GetMapping("/available")
    public ResponseEntity<List<RestPerformance>> getPerformancesWithAvailableTickets() {
        try {
            logger.info("Fetching REST performances with available tickets");
            List<RestPerformance> performances = performanceRepository.findPerformancesWithAvailableTickets();
            return new ResponseEntity<>(performances, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching REST performances with available tickets: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}