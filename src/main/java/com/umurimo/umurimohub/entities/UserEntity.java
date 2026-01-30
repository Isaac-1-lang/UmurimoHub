package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", length = 100, nullable = false)
    private String password;
    
    @Column(name = "role", nullable = false)
    private String role; // CEO, HR
    
    @Column(name = "status")
    private String status; // ACTIVE, INACTIVE
    
    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "password_changed")
    private Boolean passwordChanged = false; // For HR first login
    
    @OneToMany(mappedBy = "hrUser", cascade = CascadeType.ALL)
    private List<HRActivityLog> activityLogs;

    // Constructors
    public UserEntity() {
        this.createdAt = new Date();
        this.status = "ACTIVE";
        this.passwordChanged = false;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(Boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public List<HRActivityLog> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(List<HRActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }
}
