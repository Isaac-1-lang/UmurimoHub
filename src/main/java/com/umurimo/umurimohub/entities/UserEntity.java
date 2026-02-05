package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * UserEntity
 *
 * Represents a user in the system (e.g., CEO, HR).
 * This entity maps to the "users" table in the database and stores
 * authentication
 * and profile information.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
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

    /**
     * Default constructor for UserEntity.
     * Initializes creation date and default status.
     */
    public UserEntity() {
        this.createdAt = new Date();
        this.status = "ACTIVE";
        this.passwordChanged = false;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the encrypted password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the encrypted password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role of the user (e.g., CEO, HR).
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the account status of the user.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the account status of the user.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the date when the user account was created.
     *
     * @return the creation date
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date when the user account was created.
     *
     * @param createdAt the creation date to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Checks if the user has changed their initial password.
     *
     * @return true if password has been changed, false otherwise
     */
    public Boolean getPasswordChanged() {
        return passwordChanged;
    }

    /**
     * Sets the password change status.
     *
     * @param passwordChanged true if password has been changed, false otherwise
     */
    public void setPasswordChanged(Boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    /**
     * Gets the list of activity logs associated with this user.
     *
     * @return the list of activity logs
     */
    public List<HRActivityLog> getActivityLogs() {
        return activityLogs;
    }

    /**
     * Sets the list of activity logs associated with this user.
     *
     * @param activityLogs the list of activity logs to set
     */
    public void setActivityLogs(List<HRActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }
}
