package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.DeductionEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

/**
 * DeductionDAO
 *
 * Data Access Object for Deduction entities.
 * Manages database interactions for worker deductions including creation,
 * retrieval,
 * update, and deletion, as well as aggregation queries.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class DeductionDAO {

    /**
     * Persists a new deduction record to the database.
     *
     * @param deduction the deduction entity to save
     */
    public void save(DeductionEntity deduction) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(deduction);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Finds a deduction record by its ID.
     *
     * @param id the deduction ID
     * @return the found DeductionEntity, or null if not found
     */
    public DeductionEntity findById(Integer id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(DeductionEntity.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves all deduction records ordered by date descending.
     *
     * @return a list of all deduction entities
     */
    public List<DeductionEntity> findAll() {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<DeductionEntity> query = em.createQuery(
                    "SELECT d FROM DeductionEntity d JOIN FETCH d.worker ORDER BY d.date DESC", DeductionEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves deduction records for a specific worker.
     *
     * @param workerId the ID of the worker
     * @return a list of deduction entities for the worker
     */
    public List<DeductionEntity> findByWorkerId(String workerId) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<DeductionEntity> query = em.createQuery(
                    "SELECT d FROM DeductionEntity d JOIN FETCH d.worker WHERE d.worker.workerId = :workerId ORDER BY d.date DESC",
                    DeductionEntity.class);
            query.setParameter("workerId", workerId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves deduction records within a specific date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of deduction entities within the range
     */
    public List<DeductionEntity> findByDateRange(Date startDate, Date endDate) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<DeductionEntity> query = em.createQuery(
                    "SELECT d FROM DeductionEntity d JOIN FETCH d.worker WHERE d.date BETWEEN :startDate AND :endDate ORDER BY d.date DESC",
                    DeductionEntity.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Updates an existing deduction record.
     *
     * @param deduction the deduction entity to update
     */
    public void update(DeductionEntity deduction) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(deduction);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Deletes a deduction record by its ID.
     *
     * @param id the ID of the deduction record to delete
     */
    public void delete(Integer id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            DeductionEntity deduction = em.find(DeductionEntity.class, id);
            if (deduction != null) {
                em.remove(deduction);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Calculates the total amount of deductions for a specific worker.
     *
     * @param workerId the ID of the worker
     * @return the total deduction amount, or 0 if no deductions found
     */
    public Integer getTotalDeductionsByWorker(String workerId) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT SUM(d.amount) FROM DeductionEntity d WHERE d.worker.workerId = :workerId", Long.class);
            query.setParameter("workerId", workerId);
            Long result = query.getSingleResult();
            return result != null ? result.intValue() : 0;
        } finally {
            em.close();
        }
    }
}
