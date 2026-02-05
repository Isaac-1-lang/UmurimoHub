package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.DeductionDAO;
import com.umurimo.umurimohub.daos.WorkerDAO;
import com.umurimo.umurimohub.dtos.DeductionDTO;
import com.umurimo.umurimohub.entities.DeductionEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DeductionService
 *
 * Service implementation for managing worker deductions.
 * Handles the creation, retrieval, and aggregation of financial deductions for
 * workers.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class DeductionService {
    private DeductionDAO deductionDAO = new DeductionDAO();
    private WorkerDAO workerDAO = new WorkerDAO();

    /**
     * Creates a new deduction record for a worker.
     *
     * @param workerId the ID of the worker
     * @param amount   the amount to deduct
     * @param reason   the reason for the deduction
     * @param date     the date of the deduction (defaults to now if null)
     * @return the created DeductionEntity
     * @throws RuntimeException if the worker is not found
     */
    public DeductionEntity createDeduction(String workerId, Integer amount, String reason, Date date) {
        WorkerEntity worker = workerDAO.findById(workerId);
        if (worker == null) {
            throw new RuntimeException("Worker not found");
        }

        DeductionEntity deduction = new DeductionEntity();
        deduction.setWorker(worker);
        deduction.setAmount(amount);
        deduction.setReason(reason);
        deduction.setDate(date != null ? date : new Date());

        deductionDAO.save(deduction);
        return deduction;
    }

    /**
     * Retrieves deduction history for a specific worker.
     *
     * @param workerId the ID of the worker
     * @return a list of DeductionDTOs containing deduction details
     */
    public List<DeductionDTO> getDeductionsByWorker(String workerId) {
        List<DeductionEntity> deductions = deductionDAO.findByWorkerId(workerId);
        List<DeductionDTO> dtos = new ArrayList<>();
        for (DeductionEntity deduction : deductions) {
            DeductionDTO dto = new DeductionDTO();
            dto.setId(deduction.getId());
            dto.setAmount(deduction.getAmount());
            dto.setReason(deduction.getReason());
            dto.setDate(deduction.getDate());
            dto.setWorkerId(deduction.getWorker().getWorkerId());
            dto.setWorkerName(deduction.getWorker().getFirstName() + " " + deduction.getWorker().getLastName());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves all deductions in the system.
     *
     * @return a list of all DeductionDTOs
     */
    public List<DeductionDTO> getAllDeductions() {
        List<DeductionEntity> deductions = deductionDAO.findAll();
        List<DeductionDTO> dtos = new ArrayList<>();
        for (DeductionEntity deduction : deductions) {
            DeductionDTO dto = new DeductionDTO();
            dto.setId(deduction.getId());
            dto.setAmount(deduction.getAmount());
            dto.setReason(deduction.getReason());
            dto.setDate(deduction.getDate());
            dto.setWorkerId(deduction.getWorker().getWorkerId());
            dto.setWorkerName(deduction.getWorker().getFirstName() + " " + deduction.getWorker().getLastName());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves deductions within a specific date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of DeductionDTOs within the date range
     */
    public List<DeductionDTO> getDeductionsByDateRange(Date startDate, Date endDate) {
        List<DeductionEntity> deductions = deductionDAO.findByDateRange(startDate, endDate);
        List<DeductionDTO> dtos = new ArrayList<>();
        for (DeductionEntity deduction : deductions) {
            DeductionDTO dto = new DeductionDTO();
            dto.setId(deduction.getId());
            dto.setAmount(deduction.getAmount());
            dto.setReason(deduction.getReason());
            dto.setDate(deduction.getDate());
            dto.setWorkerId(deduction.getWorker().getWorkerId());
            dto.setWorkerName(deduction.getWorker().getFirstName() + " " + deduction.getWorker().getLastName());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Calculates the total deduction amount for a specific worker.
     *
     * @param workerId the ID of the worker
     * @return the total deduction amount
     */
    public Integer getTotalDeductionsByWorker(String workerId) {
        return deductionDAO.getTotalDeductionsByWorker(workerId);
    }

    /**
     * Retrieves a specific deduction by its ID.
     *
     * @param id the deduction ID
     * @return the DeductionEntity, or null if not found
     */
    public DeductionEntity getDeductionById(Integer id) {
        return deductionDAO.findById(id);
    }
}
