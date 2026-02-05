package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.UserDAO;
import com.umurimo.umurimohub.dtos.UserDTO;
import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.utils.EmailUtil;
import com.umurimo.umurimohub.utils.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * UserService
 *
 * Service implementation for user management.
 * Handles user lifecycle operations including CEO registration, HR creation,
 * authentication,
 * password management, and retrieval of HR personnel logic.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class UserService {
    private UserDAO userDAO = new UserDAO();

    /**
     * Registers a new CEO user.
     * Checks if the email already exists before creating a new CEO account.
     *
     * @param firstName the CEO's first name
     * @param lastName  the CEO's last name
     * @param email     the CEO's email address
     * @param password  the CEO's plain text password
     * @return the created UserEntity
     * @throws RuntimeException if the email is already in use
     */
    public UserEntity registerCEO(String firstName, String lastName, String email, String password) {
        if (userDAO.emailExists(email)) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setRole("CEO");
        user.setStatus("ACTIVE");
        user.setPasswordChanged(true);

        userDAO.save(user);
        return user;
    }

    /**
     * Creates a new HR user.
     * Generates a temporary password and emails credentials to the new HR user.
     *
     * @param firstName the HR user's first name
     * @param lastName  the HR user's last name
     * @param email     the HR user's email address
     * @param hrUserId  the ID of the administrator creating this HR user (unused in
     *                  current impl, effectively context)
     * @return the created UserEntity
     * @throws RuntimeException if the email is already in use
     */
    public UserEntity createHR(String firstName, String lastName, String email, String hrUserId) {
        if (userDAO.emailExists(email)) {
            throw new RuntimeException("Email already exists");
        }

        String tempPassword = PasswordUtil.generateRandomPassword(12);
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(PasswordUtil.hashPassword(tempPassword));
        user.setRole("HR");
        user.setStatus("ACTIVE");
        user.setPasswordChanged(false);

        userDAO.save(user);
        // Try to send email, but don't fail if it doesn't work
        try {
            EmailUtil.sendHRCredentials(email, firstName, tempPassword);
        } catch (Exception e) {
            // Log the error but continue - account is created successfully
            System.err.println("Failed to send email credentials: " + e.getMessage());
            // In production, you might want to log this to a proper logging framework
        }
        return user;
    }

    /**
     * Authenticates a user based on email and password.
     *
     * @param email    the user's email
     * @param password the user's plain text password
     * @return the authenticated UserEntity, or null if authentication fails
     */
    public UserEntity authenticate(String email, String password) {
        UserEntity user = userDAO.findByEmail(email);
        if (user == null) {
            return null;
        }

        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            return null;
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            return null;
        }

        return user;
    }

    /**
     * Changes a user's password.
     * Verifies the old password before updating to the new one.
     *
     * @param userId      the ID of the user
     * @param oldPassword the current password
     * @param newPassword the new password
     * @return true if the password was successfully changed, false otherwise
     */
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        UserEntity user = userDAO.findById(userId);
        if (user == null) {
            return false;
        }

        if (!PasswordUtil.verifyPassword(oldPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(PasswordUtil.hashPassword(newPassword));
        user.setPasswordChanged(true);
        userDAO.update(user);
        return true;
    }

    /**
     * Retrieves all HR users in the system.
     *
     * @return a list of UserDTOs representing HR personnel
     */
    public List<UserDTO> getAllHRUsers() {
        List<UserEntity> hrUsers = userDAO.findByRole("HR");
        List<UserDTO> dtos = new ArrayList<>();
        for (UserEntity user : hrUsers) {
            UserDTO dto = new UserDTO();
            dto.setUserId(user.getUserId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());
            dto.setStatus(user.getStatus());
            dto.setCreatedAt(user.getCreatedAt());
            dto.setPasswordChanged(user.getPasswordChanged());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the user ID
     * @return the UserEntity, or null if not found
     */
    public UserEntity getUserById(String userId) {
        return userDAO.findById(userId);
    }
}
