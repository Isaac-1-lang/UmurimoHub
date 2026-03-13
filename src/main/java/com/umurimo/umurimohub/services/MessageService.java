package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.MessageDAO;
import com.umurimo.umurimohub.entities.MessageEntity;

import java.util.Date;
import java.util.List;

/**
 * MessageService
 *
 * Service layer for chat messages.
 */
public class MessageService {
    private MessageDAO messageDAO = new MessageDAO();

    public MessageEntity sendMessage(String senderId, String receiverId, String content) {
        MessageEntity message = new MessageEntity();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(new Date());
        message.setIsRead(false);
        
        messageDAO.save(message);
        return message;
    }

    public List<MessageEntity> getConversationHistory(String user1Id, String user2Id) {
        return messageDAO.getConversationHistory(user1Id, user2Id);
    }

    public void markMessagesAsRead(String senderId, String receiverId) {
        messageDAO.markMessagesAsRead(senderId, receiverId);
    }
    
    public Long getUnreadCount(String receiverId) {
        return messageDAO.getUnreadCount(receiverId);
    }
}
