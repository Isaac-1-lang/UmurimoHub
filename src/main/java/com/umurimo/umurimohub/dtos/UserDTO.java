package com.umurimo.umurimohub.dtos;

import java.util.Date;

/**
 * UserDTO
 *
 * Data Transfer Object for User information.
 * This class is responsible for transferring user details, such as name, email,
 * and role,
 * between the application layers.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
public class UserDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String status;
    private Date createdAt;
    private Boolean passwordChanged;

    /**
     * Default constructor for UserDTO.
     */
    public UserDTO() {
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
     * Gets the role of the user (e.g., ADMIN, USER).
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
     * Gets the status of the user account (e.g., ACTIVE, INACTIVE).
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the user account.
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
     * Checks if the user has changed their password.
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
}
