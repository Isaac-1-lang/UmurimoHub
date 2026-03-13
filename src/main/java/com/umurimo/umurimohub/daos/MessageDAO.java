package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.MessageEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * MessageDAO
 *
 * Data Access Object for handling CRUD operations on MessageEntity.
 */
public class MessageDAO {

    protected EntityManager getEntityManager() {
        return DBConnection.getEntityManager();
    }

    public void save(MessageEntity message) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(message);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<MessageEntity> getConversationHistory(String user1Id, String user2Id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<MessageEntity> query = em.createQuery(
                    "SELECT m FROM MessageEntity m WHERE (m.senderId = :u1 AND m.receiverId = :u2) " +
                            "OR (m.senderId = :u2 AND m.receiverId = :u1) ORDER BY m.timestamp ASC",
                    MessageEntity.class);
            query.setParameter("u1", user1Id);
            query.setParameter("u2", user2Id);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void markMessagesAsRead(String senderId, String receiverId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE MessageEntity m SET m.isRead = true WHERE m.senderId = :senderId AND m.receiverId = :receiverId AND m.isRead = false")
                    .setParameter("senderId", senderId)
                    .setParameter("receiverId", receiverId)
                    .executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public Long getUnreadCount(String receiverId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(m) FROM MessageEntity m WHERE m.receiverId = :receiverId AND m.isRead = false",
                    Long.class);
            query.setParameter("receiverId", receiverId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
