package com.umurimo.umurimohub.dtos;

public class ContactDTO {
    private String id;
    private String name;
    private String role;
    private Long unreadCount;

    public ContactDTO(String id, String name, String role, Long unreadCount) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.unreadCount = unreadCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Long unreadCount) {
        this.unreadCount = unreadCount;
    }
}
