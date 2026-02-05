package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.WorkerDAO;
import com.umurimo.umurimohub.dtos.WorkerDTO;
import com.umurimo.umurimohub.entities.WorkerEntity;
import com.umurimo.umurimohub.utils.EmailUtil;
import com.umurimo.umurimohub.utils.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * WorkerService
 *
 * Service implementation for worker management.
 * Handles worker lifecycle operations including registration, authentication,
 * data retrieval (individual and bulk), and updates.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class WorkerService {
    private WorkerDAO workerDAO = new WorkerDAO();

    /**
     * Registers a new worker.
     * Generates a temporary password and emails credentials to the worker.
     *
     * @param firstName   the worker's first name
     * @param lastName    the worker's last name
     * @param email       the worker's email address
     * @param phoneNumber the worker's phone number
     * @param baseSalary  the worker's base salary
     * @return the created WorkerEntity
     * @throws RuntimeException if the email is already in use
     */
    public WorkerEntity createWorker(String firstName, String lastName, String email,
            String phoneNumber, Integer baseSalary) {
        if (workerDAO.emailExists(email)) {
            throw new RuntimeException("Email already exists");
        }

        String tempPassword = PasswordUtil.generateRandomPassword(12);
        WorkerEntity worker = new WorkerEntity();
        worker.setFirstName(firstName);
        worker.setLastName(lastName);
        worker.setEmail(email);
        worker.setPhoneNumber(phoneNumber);
        worker.setBaseSalary(baseSalary);
        worker.setPassword(PasswordUtil.hashPassword(tempPassword));
        worker.setStatus("ACTIVE");

        workerDAO.save(worker);
        // Try to send email, but don't fail if it doesn't work
        try {
            EmailUtil.sendWorkerCredentials(email, firstName, tempPassword);
        } catch (Exception e) {
            // Log the error but continue - worker account is created successfully
            System.err.println("Failed to send email credentials: " + e.getMessage());
            // In production, you might want to log this to a proper logging framework
        }
        return worker;
    }

    /**
     * Authenticates a worker based on email and password.
     *
     * @param email    the worker's email
     * @param password the worker's plain text password
     * @return the authenticated WorkerEntity, or null if authentication fails
     */
    public WorkerEntity authenticate(String email, String password) {
        WorkerEntity worker = workerDAO.findByEmail(email);
        if (worker == null) {
            return null;
        }

        if (!PasswordUtil.verifyPassword(password, worker.getPassword())) {
            return null;
        }

        if (!"ACTIVE".equals(worker.getStatus())) {
            return null;
        }

        return worker;
    }

    /**
     * Retrieves a worker by their ID.
     *
     * @param workerId the worker ID
     * @return the WorkerEntity, or null if not found
     */
    public WorkerEntity getWorkerById(String workerId) {
        return workerDAO.findById(workerId);
    }

    /**
     * Retrieves a worker by their email address.
     *
     * @param email the worker's email
     * @return the WorkerEntity, or null if not found
     */
    public WorkerEntity getWorkerByEmail(String email) {
        return workerDAO.findByEmail(email);
    }

    /**
     * Retrieves all workers in the system.
     *
     * @return a list of WorkerDTOs for all workers
     */
    public List<WorkerDTO> getAllWorkers() {
        List<WorkerEntity> workers = workerDAO.findAll();
        List<WorkerDTO> dtos = new ArrayList<>();
        for (WorkerEntity worker : workers) {
            WorkerDTO dto = new WorkerDTO();
            dto.setWorkerId(worker.getWorkerId());
            dto.setFirstName(worker.getFirstName());
            dto.setLastName(worker.getLastName());
            dto.setEmail(worker.getEmail());
            dto.setPhoneNumber(worker.getPhoneNumber());
            dto.setHireDate(worker.getHireDate());
            dto.setBaseSalary(worker.getBaseSalary());
            dto.setStatus(worker.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves all active workers.
     *
     * @return a list of WorkerDTOs for active workers
     */
    public List<WorkerDTO> getActiveWorkers() {
        List<WorkerEntity> workers = workerDAO.findByStatus("ACTIVE");
        List<WorkerDTO> dtos = new ArrayList<>();
        for (WorkerEntity worker : workers) {
            WorkerDTO dto = new WorkerDTO();
            dto.setWorkerId(worker.getWorkerId());
            dto.setFirstName(worker.getFirstName());
            dto.setLastName(worker.getLastName());
            dto.setEmail(worker.getEmail());
            dto.setPhoneNumber(worker.getPhoneNumber());
            dto.setHireDate(worker.getHireDate());
            dto.setBaseSalary(worker.getBaseSalary());
            dto.setStatus(worker.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Updates an existing worker record.
     *
     * @param worker the worker entity with updated values
     */
    public void updateWorker(WorkerEntity worker) {
        workerDAO.update(worker);
    }
}
