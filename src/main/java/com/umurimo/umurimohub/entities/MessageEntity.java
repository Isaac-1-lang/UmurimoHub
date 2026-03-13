package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

/**
 * MessageEntity
 *
 * Represents a direct chat message between two users (CEO, HR, or Worker).
 * Uses Strings for senderId and receiverId since users can be from either 
 * the 'users' table or the 'workers' table.
 */
@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "sender_id", nullable = false)
    private String senderId;

    @Column(name = "receiver_id", nullable = false)
    private String receiverId;

    @Column(name = "content", nullable = false, length = 2000)
    private String content;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    public MessageEntity() {
        this.timestamp = new Date();
        this.isRead = false;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
