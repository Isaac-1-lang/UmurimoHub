package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.WorkerDAO;
import com.umurimo.umurimohub.dtos.WorkerDTO;
import com.umurimo.umurimohub.entities.WorkerEntity;
import com.umurimo.umurimohub.utils.EmailUtil;
import com.umurimo.umurimohub.utils.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

public class WorkerService {
    private WorkerDAO workerDAO = new WorkerDAO();

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

    public WorkerEntity getWorkerById(String workerId) {
        return workerDAO.findById(workerId);
    }

    public WorkerEntity getWorkerByEmail(String email) {
        return workerDAO.findByEmail(email);
    }

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

    public void updateWorker(WorkerEntity worker) {
        workerDAO.update(worker);
    }
}
