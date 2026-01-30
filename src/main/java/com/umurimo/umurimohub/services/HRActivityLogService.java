package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.HRActivityLogDAO;
import com.umurimo.umurimohub.daos.UserDAO;
import com.umurimo.umurimohub.entities.HRActivityLog;
import com.umurimo.umurimohub.entities.UserEntity;

public class HRActivityLogService {
    private HRActivityLogDAO logDAO = new HRActivityLogDAO();
    private UserDAO userDAO = new UserDAO();

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
