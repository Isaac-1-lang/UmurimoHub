package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.PunishmentEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

/**
 * PunishmentDAO
 *
 * Data Access Object for Punishment entities.
 * Handles database operations for worker punishments including saving,
 * retrieving,
 * updating, and deleting records.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class PunishmentDAO {

    /**
     * Persists a new punishment record to the database.
     *
     * @param punishment the punishment entity to save
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
     * Persists a new punishment record to the database.
     *
     * @param punishment the punishment entity to save
     */
    public void save(PunishmentEntity punishment) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(punishment);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Finds a punishment record by its ID.
     *
     * @param id the punishment ID
     * @return the found PunishmentEntity, or null if not found
     */
    public PunishmentEntity findById(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PunishmentEntity.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves all punishment records ordered by date descending.
     *
     * @return a list of all punishment entities
     */
    public List<PunishmentEntity> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PunishmentEntity> query = em.createQuery(
                    "SELECT p FROM PunishmentEntity p JOIN FETCH p.worker ORDER BY p.date DESC",
                    PunishmentEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves punishment records for a specific worker.
     *
     * @param workerId the ID of the worker
     * @return a list of punishment entities for the worker
     */
    public List<PunishmentEntity> findByWorkerId(String workerId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PunishmentEntity> query = em.createQuery(
                    "SELECT p FROM PunishmentEntity p JOIN FETCH p.worker WHERE p.worker.workerId = :workerId ORDER BY p.date DESC",
                    PunishmentEntity.class);
            query.setParameter("workerId", workerId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves punishment records within a specific date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of punishment entities within the range
     */
    public List<PunishmentEntity> findByDateRange(Date startDate, Date endDate) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PunishmentEntity> query = em.createQuery(
                    "SELECT p FROM PunishmentEntity p JOIN FETCH p.worker WHERE p.date BETWEEN :startDate AND :endDate ORDER BY p.date DESC",
                    PunishmentEntity.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Updates an existing punishment record.
     *
     * @param punishment the punishment entity to update
     */
    public void update(PunishmentEntity punishment) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(punishment);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Deletes a punishment record by its ID.
     *
     * @param id the ID of the punishment record to delete
     */
    public void delete(String id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            PunishmentEntity punishment = em.find(PunishmentEntity.class, id);
            if (punishment != null) {
                em.remove(punishment);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
