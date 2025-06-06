package controller;

import model.RestArtist;
import repository.RestArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rest/artists")
@CrossOrigin(origins = "*")
public class RestArtistController {

    private static final Logger logger = LoggerFactory.getLogger(RestArtistController.class);

    @Autowired
    private RestArtistRepository artistRepository;

    @GetMapping
    public ResponseEntity<List<RestArtist>> getAllArtists() {
        try {
            logger.info("Fetching all REST artists");
            List<RestArtist> artists = artistRepository.findAll();
            return new ResponseEntity<>(artists, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all REST artists: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestArtist> getArtistById(@PathVariable int id) {
        try {
            Optional<RestArtist> artist = artistRepository.findById(id);
            return artist.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error fetching REST artist with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<RestArtist> createArtist(@RequestBody RestArtist artist) {
        try {
            artist.setArtistID(0);
            RestArtist savedArtist = artistRepository.save(artist);
            return new ResponseEntity<>(savedArtist, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating REST artist: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestArtist> updateArtist(@PathVariable int id, @RequestBody RestArtist artist) {
        try {
            if (artistRepository.existsById(id)) {
                artist.setArtistID(id);
                RestArtist updatedArtist = artistRepository.save(artist);
                return new ResponseEntity<>(updatedArtist, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating REST artist with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable int id) {
        try {
            if (artistRepository.existsById(id)) {
                artistRepository.deleteById(id);
                return new ResponseEntity<>("REST Artist deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("REST Artist not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting REST artist with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>("Error deleting REST artist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}