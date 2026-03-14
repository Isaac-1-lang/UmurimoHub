package com.umurimo.umurimohub.websockets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.umurimo.umurimohub.entities.MessageEntity;
import com.umurimo.umurimohub.services.MessageService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/chat/{userId}")
public class ChatEndpoint {

    private static final Map<String, Session> activeSessions = new ConcurrentHashMap<>();
    private static final MessageService messageService = new MessageService();
    private static final Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        activeSessions.put(userId, session);
        System.out.println("WebSocket opened for user: " + userId);
    }

    @OnMessage
    public void onMessage(String messageJson, Session session, @PathParam("userId") String senderId) {
        try {
            JsonObject jsonObject = gson.fromJson(messageJson, JsonObject.class);
            String type = jsonObject.has("type") ? jsonObject.get("type").getAsString() : "message";

            if ("history_request".equals(type)) {
                String receiverId = jsonObject.get("receiverId").getAsString();
                List<MessageEntity> history = messageService.getConversationHistory(senderId, receiverId);
                messageService.markMessagesAsRead(receiverId, senderId); // marking incoming as read

                JsonObject response = new JsonObject();
                response.addProperty("type", "history");
                response.add("data", gson.toJsonTree(history));
                session.getBasicRemote().sendText(response.toString());
            } else if ("message".equals(type)) {
                String receiverId = jsonObject.get("receiverId").getAsString();
                String content = jsonObject.get("content").getAsString();

                MessageEntity savedMessage = messageService.sendMessage(senderId, receiverId, content);

                JsonObject msgObject = new JsonObject();
                msgObject.addProperty("type", "new_message");
                msgObject.add("data", gson.toJsonTree(savedMessage));

                // Send back to sender
                session.getBasicRemote().sendText(msgObject.toString());

                // Send to receiver if online
                Session receiverSession = activeSessions.get(receiverId);
                if (receiverSession != null && receiverSession.isOpen()) {
                    receiverSession.getBasicRemote().sendText(msgObject.toString());
                }
            } else if ("mark_read".equals(type)) {
                String partnerId = jsonObject.get("partnerId").getAsString();
                messageService.markMessagesAsRead(partnerId, senderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        activeSessions.remove(userId);
        System.out.println("WebSocket closed for user: " + userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: " + throwable.getMessage());
    }
}
