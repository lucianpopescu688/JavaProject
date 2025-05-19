/*package repository;

import model.Performance;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;

public class HibernatePerformanceRepository implements IPerformanceRepository {
    private static final Logger logger = LoggerFactory.getLogger(HibernatePerformanceRepository.class);
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
    public void insertPerformance(Performance performance) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(performance);
            transaction.commit();
            logger.info("Inserted performance ID: {}", performance.getPerformanceID());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Failed to insert performance", e);
        }
    }

    @Override
    public List<Performance> getAllPerformances() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("", Performance.class).list();
        } catch (Exception e) {
            logger.error("Failed to fetch performances", e);
            return List.of();
        }
    }

    @Override
    public List<Performance> getPerformancesByDate(LocalDateTime date) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Performance WHERE DATE(date) = DATE(:date)", Performance.class)
                    .setParameter("date", date)
                    .list();
        } catch (Exception e) {
            logger.error("Failed to fetch performances by date", e);
            return List.of();
        }
    }

    @Override
    public void updatePerformance(Performance performance) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(performance);
            transaction.commit();
            logger.info("Updated performance ID: {}", performance.getPerformanceID());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Failed to update performance", e);
        }
    }

    @Override
    public void deletePerformance(int performanceId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Performance performance = session.get(Performance.class, performanceId);
            if (performance != null) {
                session.delete(performance);
                logger.info("Deleted performance ID: {}", performanceId);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Failed to delete performance", e);
        }
    }

    @Override
    public Performance getPerformanceById(int performanceId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Performance.class, performanceId);
        } catch (Exception e) {
            logger.error("Failed to fetch performance by ID: {}", performanceId, e);
            return null;
        }
    }
}
*/
