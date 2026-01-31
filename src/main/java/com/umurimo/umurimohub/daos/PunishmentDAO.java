package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.PunishmentEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class PunishmentDAO {
    public void save(PunishmentEntity punishment) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(punishment);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public PunishmentEntity findById(String id) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(PunishmentEntity.class, id);
        } finally {
            em.close();
        }
    }

    public List<PunishmentEntity> findAll() {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<PunishmentEntity> query = em.createQuery(
                "SELECT p FROM PunishmentEntity p JOIN FETCH p.worker ORDER BY p.date DESC", PunishmentEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<PunishmentEntity> findByWorkerId(String workerId) {
        EntityManager em = DBConnection.getEntityManager();
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

    public List<PunishmentEntity> findByDateRange(Date startDate, Date endDate) {
        EntityManager em = DBConnection.getEntityManager();
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

    public void update(PunishmentEntity punishment) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(punishment);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(String id) {
        EntityManager em = DBConnection.getEntityManager();
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
