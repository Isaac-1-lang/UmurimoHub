package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.AttendanceEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class AttendanceDAO {
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

    public AttendanceEntity findById(String id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(AttendanceEntity.class, id);
        } finally {
            em.close();
        }
    }

    public List<AttendanceEntity> findAll() {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<AttendanceEntity> query = em.createQuery(
                "SELECT a FROM AttendanceEntity a JOIN FETCH a.worker ORDER BY a.date DESC", AttendanceEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

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
