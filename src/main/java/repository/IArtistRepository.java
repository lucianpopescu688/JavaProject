package repository;
import model.Artist;
import java.util.List;

public interface IArtistRepository
{
    void addArtist(Artist artist);
    Artist getArtistByID(int artistID);
    List<Artist> getAllArtists();
}