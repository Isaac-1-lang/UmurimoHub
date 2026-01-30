package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.WorkerEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class WorkerDAO {
    public void save(WorkerEntity worker) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(worker);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public WorkerEntity findById(String workerId) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(WorkerEntity.class, workerId);
        } finally {
            em.close();
        }
    }

    public WorkerEntity findByEmail(String email) {
        EntityManager em = DBConnection.getEntityManager();
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

    public List<WorkerEntity> findAll() {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<WorkerEntity> query = em.createQuery(
                "SELECT w FROM WorkerEntity w ORDER BY w.hireDate DESC", WorkerEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<WorkerEntity> findByStatus(String status) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<WorkerEntity> query = em.createQuery(
                "SELECT w FROM WorkerEntity w WHERE w.status = :status", WorkerEntity.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void update(WorkerEntity worker) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(worker);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(String workerId) {
        EntityManager em = DBConnection.getEntityManager();
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

    public boolean emailExists(String email) {
        EntityManager em = DBConnection.getEntityManager();
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
