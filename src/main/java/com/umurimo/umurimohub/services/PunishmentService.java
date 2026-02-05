package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.PunishmentDAO;
import com.umurimo.umurimohub.daos.WorkerDAO;
import com.umurimo.umurimohub.dtos.PunishmentDTO;
import com.umurimo.umurimohub.entities.PunishmentEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PunishmentService
 *
 * Service implementation for managing worker punishments.
 * Handles the issuance of punishments, retrieval of punishment history, and
 * reporting on disciplinary actions.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class PunishmentService {
    private PunishmentDAO punishmentDAO = new PunishmentDAO();
    private WorkerDAO workerDAO = new WorkerDAO();

    /**
     * Issues a new punishment for a worker.
     *
     * @param workerId    the ID of the worker receiving the punishment
     * @param title       the title/subject of the punishment
     * @param description detailed description of the violation
     * @param date        the date of the punishment (defaults to now if null)
     * @return the created PunishmentEntity
     * @throws RuntimeException if the worker is not found
     */
    public PunishmentEntity createPunishment(String workerId, String title, String description, Date date) {
        WorkerEntity worker = workerDAO.findById(workerId);
        if (worker == null) {
            throw new RuntimeException("Worker not found");
        }

        PunishmentEntity punishment = new PunishmentEntity();
        punishment.setWorker(worker);
        punishment.setTitle(title);
        punishment.setDescription(description);
        punishment.setDate(date != null ? date : new Date());

        punishmentDAO.save(punishment);
        return punishment;
    }

    /**
     * Retrieves punishment history for a specific worker.
     *
     * @param workerId the ID of the worker
     * @return a list of PunishmentDTOs containing punishment details
     */
    public List<PunishmentDTO> getPunishmentsByWorker(String workerId) {
        List<PunishmentEntity> punishments = punishmentDAO.findByWorkerId(workerId);
        List<PunishmentDTO> dtos = new ArrayList<>();
        for (PunishmentEntity punishment : punishments) {
            PunishmentDTO dto = new PunishmentDTO();
            dto.setId(punishment.getId());
            dto.setTitle(punishment.getTitle());
            dto.setDescription(punishment.getDescription());
            dto.setDate(punishment.getDate());
            dto.setWorkerId(punishment.getWorker().getWorkerId());
            dto.setWorkerName(punishment.getWorker().getFirstName() + " " + punishment.getWorker().getLastName());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves all punishments in the system.
     *
     * @return a list of all PunishmentDTOs
     */
    public List<PunishmentDTO> getAllPunishments() {
        List<PunishmentEntity> punishments = punishmentDAO.findAll();
        List<PunishmentDTO> dtos = new ArrayList<>();
        for (PunishmentEntity punishment : punishments) {
            PunishmentDTO dto = new PunishmentDTO();
            dto.setId(punishment.getId());
            dto.setTitle(punishment.getTitle());
            dto.setDescription(punishment.getDescription());
            dto.setDate(punishment.getDate());
            dto.setWorkerId(punishment.getWorker().getWorkerId());
            dto.setWorkerName(punishment.getWorker().getFirstName() + " " + punishment.getWorker().getLastName());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves punishments within a specific date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of PunishmentDTOs within the date range
     */
    public List<PunishmentDTO> getPunishmentsByDateRange(Date startDate, Date endDate) {
        List<PunishmentEntity> punishments = punishmentDAO.findByDateRange(startDate, endDate);
        List<PunishmentDTO> dtos = new ArrayList<>();
        for (PunishmentEntity punishment : punishments) {
            PunishmentDTO dto = new PunishmentDTO();
            dto.setId(punishment.getId());
            dto.setTitle(punishment.getTitle());
            dto.setDescription(punishment.getDescription());
            dto.setDate(punishment.getDate());
            dto.setWorkerId(punishment.getWorker().getWorkerId());
            dto.setWorkerName(punishment.getWorker().getFirstName() + " " + punishment.getWorker().getLastName());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves a specific punishment by its ID.
     *
     * @param id the punishment ID
     * @return the PunishmentEntity, or null if not found
     */
    public PunishmentEntity getPunishmentById(String id) {
        return punishmentDAO.findById(id);
    }
}
