package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.DeductionEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;


/**
 * @author Isaac-1-lang
 * @version 0.0.1
 */




public class DeductionDAO {
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

    public DeductionEntity findById(Integer id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(DeductionEntity.class, id);
        } finally {
            em.close();
        }
    }

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
