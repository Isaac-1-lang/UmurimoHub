package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.WorkerEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * WorkerDAO
 *
 * Data Access Object for Worker entities.
 * Facilitates database operations for workers, including registration (save),
 * authentication support (findByEmail), retrieval (all, by status), and
 * management.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class WorkerDAO {

    /**
     * Persists a new worker record to the database.
     *
     * @param worker the worker entity to save
     */
    /**
     * Protected method to get EntityManager, allowed to be overridden for testing.
     *
     * @return the EntityManager
     */
    protected EntityManager getEntityManager() {
        return DBConnection.getEntityManager();
    }

    /**
     * Persists a new worker record to the database.
     *
     * @param worker the worker entity to save
     */
    public void save(WorkerEntity worker) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(worker);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Finds a worker record by its ID.
     *
     * @param workerId the worker ID
     * @return the found WorkerEntity, or null if not found
     */
    public WorkerEntity findById(String workerId) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WorkerEntity.class, workerId);
        } finally {
            em.close();
        }
    }

    /**
     * Finds a worker by their email address.
     * Used primarily for authentication.
     *
     * @param email the email address to search for
     * @return the found WorkerEntity, or null if not found
     */
    public WorkerEntity findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<WorkerEntity> query = em.createQuery(
                    "SELECT w FROM WorkerEntity w WHERE w.email = :email", WorkerEntity.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves all worker records ordered by hire date descending.
     *
     * @return a list of all worker entities
     */
    public List<WorkerEntity> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<WorkerEntity> query = em.createQuery(
                    "SELECT w FROM WorkerEntity w ORDER BY w.hireDate DESC", WorkerEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves workers with a specific status.
     *
     * @param status the status to filter by (e.g., "ACTIVE")
     * @return a list of worker entities with the specified status
     */
    public List<WorkerEntity> findByStatus(String status) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<WorkerEntity> query = em.createQuery(
                    "SELECT w FROM WorkerEntity w WHERE w.status = :status", WorkerEntity.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Updates an existing worker record.
     *
     * @param worker the worker entity to update
     */
    public void update(WorkerEntity worker) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(worker);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Deletes a worker record by its ID.
     *
     * @param workerId the ID of the worker record to delete
     */
    public void delete(String workerId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            WorkerEntity worker = em.find(WorkerEntity.class, workerId);
            if (worker != null) {
                em.remove(worker);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Checks if a worker with the given email exists.
     *
     * @param email the email address to check
     * @return true if a worker with the email exists, false otherwise
     */
    public boolean emailExists(String email) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(w) FROM WorkerEntity w WHERE w.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
