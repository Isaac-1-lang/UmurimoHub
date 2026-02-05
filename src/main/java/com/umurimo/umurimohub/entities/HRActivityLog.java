package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

/**
 * HRActivityLog
 *
 * Entity for tracking actions performed by HR users.
 * Maps to the "hr_activity_log" table and stores details about the action,
 * timestamp, and the HR user responsible.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
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

    /**
     * Default constructor for HRActivityLog.
     * Initializes the timestamp to current date/time.
     */
    public HRActivityLog() {
        this.timestamp = new Date();
    }

    /**
     * Gets the unique identifier for the log entry.
     *
     * @return the log ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the log entry.
     *
     * @param id the log ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the action performed.
     *
     * @return the action description
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the action performed.
     *
     * @param action the action description to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets the timestamp when the action occurred.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp when the action occurred.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets the HR user who performed the action.
     *
     * @return the user entity
     */
    public UserEntity getHrUser() {
        return hrUser;
    }

    /**
     * Sets the HR user who performed the action.
     *
     * @param hrUser the user entity to set
     */
    public void setHrUser(UserEntity hrUser) {
        this.hrUser = hrUser;
    }

    /**
     * Gets additional details about the action.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets additional details about the action.
     *
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }
}
