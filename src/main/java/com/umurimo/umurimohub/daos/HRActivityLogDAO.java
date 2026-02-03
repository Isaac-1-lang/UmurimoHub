package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.HRActivityLog;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;



/**
 * @author Isaac-1-lang
 * @version 0.0.1
 */



public class HRActivityLogDAO {
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

    public HRActivityLog findById(String id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(HRActivityLog.class, id);
        } finally {
            em.close();
        }
    }

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
