package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * UserDAO
 *
 * Data Access Object for User entities.
 * Manages database operations for system users (CEO, HR), including
 * authentication support
 * (findByEmail), CRUD operations, and existence checks.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class UserDAO {

    /**
     * Persists a new user record to the database.
     *
     * @param user the user entity to save
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
     * Persists a new user record to the database.
     *
     * @param user the user entity to save
     */
    public void save(UserEntity user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Finds a user record by its ID.
     *
     * @param userId the user ID
     * @return the found UserEntity, or null if not found
     */
    public UserEntity findById(String userId) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserEntity.class, userId);
        } finally {
            em.close();
        }
    }

    /**
     * Finds a user by their email address.
     * Used primarily for authentication.
     *
     * @param email the email address to search for
     * @return the found UserEntity, or null if not found
     */
    public UserEntity findByEmail(String email) {
        EntityManager em = getEntityManager();
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

    /**
     * Retrieves all user records.
     *
     * @return a list of all user entities
     */
    public List<UserEntity> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<UserEntity> query = em.createQuery(
                    "SELECT u FROM UserEntity u", UserEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Retrieves users with a specific role.
     *
     * @param role the role to filter by (e.g., "HR")
     * @return a list of user entities with the specified role
     */
    public List<UserEntity> findByRole(String role) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<UserEntity> query = em.createQuery(
                    "SELECT u FROM UserEntity u WHERE u.role = :role", UserEntity.class);
            query.setParameter("role", role);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Updates an existing user record.
     *
     * @param user the user entity to update
     */
    public void update(UserEntity user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Deletes a user record by its ID.
     *
     * @param userId the ID of the user record to delete
     */
    public void delete(String userId) {
        EntityManager em = getEntityManager();
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

    /**
     * Checks if a user with the given email exists.
     *
     * @param email the email address to check
     * @return true if a user with the email exists, false otherwise
     */
    public boolean emailExists(String email) {
        EntityManager em = getEntityManager();
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
