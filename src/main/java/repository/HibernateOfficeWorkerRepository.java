package repository;

import model.OfficeWorker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateOfficeWorkerRepository implements IOfficeWorkerRepository {
    private static final Logger logger = LoggerFactory.getLogger(HibernateOfficeWorkerRepository.class);
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
    public void addOfficeWorker(OfficeWorker worker) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(worker);
            transaction.commit();
            logger.info("Added office worker: {}", worker.getOfficeWorkerUsername());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Failed to add office worker", e);
        }
    }

    @Override
    public OfficeWorker authenticate(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM OfficeWorker WHERE officeWorkerUsername = :username AND officeWorkerPassword = :password",
                            OfficeWorker.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
        } catch (Exception e) {
            logger.error("Authentication error", e);
            return null;
        }
    }
}