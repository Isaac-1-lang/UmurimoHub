package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.AttendanceEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

/**
 * AttendanceDAO
 *
 * Data Access Object for Attendance entities.
 * handles database operations related to worker attendance such as saving,
 * retrieving,
 * updating, and deleting attendance records.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class AttendanceDAO {

    /**
     * Persists a new attendance record to the database.
     *
     * @param attendance the attendance entity to save
     */
    public void save(AttendanceEntity attendance) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(attendance);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Finds an attendance record by its ID.
     *
     * @param id the attendance ID
     * @return the found AttendanceEntity, or null if not found
     */
    public AttendanceEntity findById(String id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(AttendanceEntity.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves all attendance records ordered by date descending.
     *
     * @return a list of all attendance entities
     */
    public List<AttendanceEntity> findAll() {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<AttendanceEntity> query = em.createQuery(
                    "SELECT a FROM AttendanceEntity a JOIN FETCH a.worker ORDER BY a.date DESC",
                    AttendanceEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves attendance records for a specific worker.
     *
     * @param workerId the ID of the worker
     * @return a list of attendance entities for the worker
     */
    public List<AttendanceEntity> findByWorkerId(String workerId) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<AttendanceEntity> query = em.createQuery(
                    "SELECT a FROM AttendanceEntity a JOIN FETCH a.worker WHERE a.worker.workerId = :workerId ORDER BY a.date DESC",
                    AttendanceEntity.class);
            query.setParameter("workerId", workerId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves attendance records within a specific date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of attendance entities within the range
     */
    public List<AttendanceEntity> findByDateRange(Date startDate, Date endDate) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<AttendanceEntity> query = em.createQuery(
                    "SELECT a FROM AttendanceEntity a JOIN FETCH a.worker WHERE a.date BETWEEN :startDate AND :endDate ORDER BY a.date DESC",
                    AttendanceEntity.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves attendance records for a specific worker within a date range.
     *
     * @param workerId  the ID of the worker
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of matching attendance entities
     */
    public List<AttendanceEntity> findByWorkerAndDateRange(String workerId, Date startDate, Date endDate) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<AttendanceEntity> query = em.createQuery(
                    "SELECT a FROM AttendanceEntity a JOIN FETCH a.worker WHERE a.worker.workerId = :workerId " +
                            "AND a.date BETWEEN :startDate AND :endDate ORDER BY a.date DESC",
                    AttendanceEntity.class);
            query.setParameter("workerId", workerId);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Updates an existing attendance record.
     *
     * @param attendance the attendance entity to update
     */
    public void update(AttendanceEntity attendance) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(attendance);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Deletes an attendance record by its ID.
     *
     * @param id the ID of the attendance record to delete
     */
    public void delete(String id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            AttendanceEntity attendance = em.find(AttendanceEntity.class, id);
            if (attendance != null) {
                em.remove(attendance);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
