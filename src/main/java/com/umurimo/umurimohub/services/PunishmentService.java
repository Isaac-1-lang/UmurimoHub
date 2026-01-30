package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.PunishmentDAO;
import com.umurimo.umurimohub.daos.WorkerDAO;
import com.umurimo.umurimohub.dtos.PunishmentDTO;
import com.umurimo.umurimohub.entities.PunishmentEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PunishmentService {
    private PunishmentDAO punishmentDAO = new PunishmentDAO();
    private WorkerDAO workerDAO = new WorkerDAO();

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

    public PunishmentEntity getPunishmentById(String id) {
        return punishmentDAO.findById(id);
    }
}
