package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;



/**
 * @author Isaac-1-lang
 * @version 0.0.1
 */


public class UserDAO {
    public void save(UserEntity user) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public UserEntity findById(String userId) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            return em.find(UserEntity.class, userId);
        } finally {
            em.close();
        }
    }

    public UserEntity findByEmail(String email) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<UserEntity> query = em.createQuery(
                "SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<UserEntity> findAll() {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<UserEntity> query = em.createQuery(
                "SELECT u FROM UserEntity u", UserEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<UserEntity> findByRole(String role) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            TypedQuery<UserEntity> query = em.createQuery(
                "SELECT u FROM UserEntity u WHERE u.role = :role", UserEntity.class);
            query.setParameter("role", role);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void update(UserEntity user) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(String userId) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            UserEntity user = em.find(UserEntity.class, userId);
            if (user != null) {
                em.remove(user);
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
                "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
