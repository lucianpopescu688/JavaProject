package service;

import model.Artist;
import repository.ArtistRepository;
import repository.HibernateArtistRepository;
import repository.IArtistRepository;
import java.util.List;

public class ArtistService {
    private final IArtistRepository artistRepo;

    public ArtistService() {
        this.artistRepo = new ArtistRepository();
    }

    // Get all artists (for future GUI expansions)
    public List<Artist> getAllArtists() {
        return artistRepo.getAllArtists();
    }

    // Get artist by ID (used when creating performances)
    public Artist getArtistById(int artistId) {
        return artistRepo.getArtistByID(artistId);
    }

    // Add new artist (optional, if GUI allows artist management)
    public void addArtist(Artist artist) {
        artistRepo.addArtist(artist);
    }
}