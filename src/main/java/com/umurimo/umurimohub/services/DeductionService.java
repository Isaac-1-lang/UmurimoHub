package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.DeductionDAO;
import com.umurimo.umurimohub.daos.WorkerDAO;
import com.umurimo.umurimohub.dtos.DeductionDTO;
import com.umurimo.umurimohub.entities.DeductionEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeductionService {
    private DeductionDAO deductionDAO = new DeductionDAO();
    private WorkerDAO workerDAO = new WorkerDAO();

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

    public Integer getTotalDeductionsByWorker(String workerId) {
        return deductionDAO.getTotalDeductionsByWorker(workerId);
    }

    public DeductionEntity getDeductionById(Integer id) {
        return deductionDAO.findById(id);
    }
}
