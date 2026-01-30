package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "hr_activity_log")
public class HRActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "log_id")
    private String id;
    
    @Column(name = "action", nullable = false, length = 500)
    private String action;
    
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity hrUser;
    
    @Column(name = "details", length = 1000)
    private String details;

    // Constructors
    public HRActivityLog() {
        this.timestamp = new Date();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public UserEntity getHrUser() {
        return hrUser;
    }

    public void setHrUser(UserEntity hrUser) {
        this.hrUser = hrUser;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
