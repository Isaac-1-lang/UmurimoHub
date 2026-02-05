package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.HRActivityLog;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

/**
 * HRActivityLogDAO
 *
 * Data Access Object for HR Activity Log entities.
 * Handles database operations for tracking HR user actions, including creation
 * and retrieval
 * based on various criteria (all, by user, by date).
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class HRActivityLogDAO {

    /**
     * Persists a new HR activity log record to the database.
     *
     * @param log the activity log entity to save
     */
    public void save(HRActivityLog log) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(log);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Finds an activity log record by its ID.
     *
     * @param id the activity log ID
     * @return the found HRActivityLog, or null if not found
     */
    public HRActivityLog findById(String id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(HRActivityLog.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves all activity logs ordered by timestamp descending.
     *
     * @return a list of all HRActivityLog entities
     */
    public List<HRActivityLog> findAll() {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<HRActivityLog> query = em.createQuery(
                    "SELECT l FROM HRActivityLog l JOIN FETCH l.hrUser ORDER BY l.timestamp DESC", HRActivityLog.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves activity logs for a specific HR user.
     *
     * @param userId the ID of the HR user
     * @return a list of activity logs for the user
     */
    public List<HRActivityLog> findByUserId(String userId) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<HRActivityLog> query = em.createQuery(
                    "SELECT l FROM HRActivityLog l JOIN FETCH l.hrUser WHERE l.hrUser.userId = :userId ORDER BY l.timestamp DESC",
                    HRActivityLog.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves activity logs within a specific date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of activity logs within the range
     */
    public List<HRActivityLog> findByDateRange(Date startDate, Date endDate) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<HRActivityLog> query = em.createQuery(
                    "SELECT l FROM HRActivityLog l JOIN FETCH l.hrUser WHERE l.timestamp BETWEEN :startDate AND :endDate ORDER BY l.timestamp DESC",
                    HRActivityLog.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
