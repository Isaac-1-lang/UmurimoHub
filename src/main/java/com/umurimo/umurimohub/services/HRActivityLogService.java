package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.HRActivityLogDAO;
import com.umurimo.umurimohub.daos.UserDAO;
import com.umurimo.umurimohub.entities.HRActivityLog;
import com.umurimo.umurimohub.entities.UserEntity;

/**
 * HRActivityLogService
 *
 * Service implementation for logging HR activities.
 * Provides a method to record actions performed by HR personnel, ensuring
 * auditability.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class HRActivityLogService {
    private HRActivityLogDAO logDAO = new HRActivityLogDAO();
    private UserDAO userDAO = new UserDAO();

    /**
     * Logs an activity performed by an HR user.
     * Verifies that the user exists and has the 'HR' role before logging.
     *
     * @param userId  the ID of the user performing the action
     * @param action  the action description
     * @param details detailed information about the action
     */
    public void logActivity(String userId, String action, String details) {
        UserEntity hrUser = userDAO.findById(userId);
        if (hrUser == null || !"HR".equals(hrUser.getRole())) {
            return; // Only log for HR users
        }

        HRActivityLog log = new HRActivityLog();
        log.setHrUser(hrUser);
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(new java.util.Date());

        logDAO.save(log);
    }
}
