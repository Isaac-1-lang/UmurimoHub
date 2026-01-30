package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.UserDAO;
import com.umurimo.umurimohub.dtos.UserDTO;
import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.utils.EmailUtil;
import com.umurimo.umurimohub.utils.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();

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
        EmailUtil.sendHRCredentials(email, firstName, tempPassword);
        return user;
    }

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

    public UserEntity getUserById(String userId) {
        return userDAO.findById(userId);
    }
}
