package repository;

import model.Artist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class HibernateArtistRepository implements IArtistRepository {
    private static final Logger logger = LoggerFactory.getLogger(HibernateArtistRepository.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void addArtist(Artist artist) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(artist);
            transaction.commit();
            logger.info("Added artist: {}", artist.getArtistName());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Failed to add artist", e);
        }
    }

    @Override
    public Artist getArtistByID(int artistId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Artist.class, artistId);
        } catch (Exception e) {
            logger.error("Failed to fetch artist with ID: {}", artistId, e);
            return null;
        }
    }

    @Override
    public List<Artist> getAllArtists() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Artist", Artist.class).list();
        } catch (Exception e) {
            logger.error("Failed to fetch artists", e);
            return List.of();
        }
    }
}