package repository;

import model.RestArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RestArtistRepository extends JpaRepository<RestArtist, Integer> {
    Optional<RestArtist> findByArtistName(String artistName);
}