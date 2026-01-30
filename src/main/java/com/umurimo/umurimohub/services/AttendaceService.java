package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.AttendanceDAO;
import com.umurimo.umurimohub.daos.WorkerDAO;
import com.umurimo.umurimohub.dtos.AttendanceDTO;
import com.umurimo.umurimohub.entities.AttendanceEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendaceService {
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private WorkerDAO workerDAO = new WorkerDAO();

    public AttendanceEntity createAttendance(String workerId, Date date, String status, String remarks) {
        WorkerEntity worker = workerDAO.findById(workerId);
        if (worker == null) {
            throw new RuntimeException("Worker not found");
        }

        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setWorker(worker);
        attendance.setDate(date);
        attendance.setStatus(status);
        attendance.setRemarks(remarks);

        attendanceDAO.save(attendance);
        return attendance;
    }

    public List<AttendanceDTO> getAttendanceByWorker(String workerId) {
        List<AttendanceEntity> attendances = attendanceDAO.findByWorkerId(workerId);
        List<AttendanceDTO> dtos = new ArrayList<>();
        for (AttendanceEntity attendance : attendances) {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(attendance.getId());
            dto.setDate(attendance.getDate());
            dto.setStatus(attendance.getStatus());
            dto.setWorkerId(attendance.getWorker().getWorkerId());
            dto.setWorkerName(attendance.getWorker().getFirstName() + " " + attendance.getWorker().getLastName());
            dto.setRemarks(attendance.getRemarks());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<AttendanceDTO> getAllAttendance() {
        List<AttendanceEntity> attendances = attendanceDAO.findAll();
        List<AttendanceDTO> dtos = new ArrayList<>();
        for (AttendanceEntity attendance : attendances) {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(attendance.getId());
            dto.setDate(attendance.getDate());
            dto.setStatus(attendance.getStatus());
            dto.setWorkerId(attendance.getWorker().getWorkerId());
            dto.setWorkerName(attendance.getWorker().getFirstName() + " " + attendance.getWorker().getLastName());
            dto.setRemarks(attendance.getRemarks());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<AttendanceDTO> getAttendanceByDateRange(Date startDate, Date endDate) {
        List<AttendanceEntity> attendances = attendanceDAO.findByDateRange(startDate, endDate);
        List<AttendanceDTO> dtos = new ArrayList<>();
        for (AttendanceEntity attendance : attendances) {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(attendance.getId());
            dto.setDate(attendance.getDate());
            dto.setStatus(attendance.getStatus());
            dto.setWorkerId(attendance.getWorker().getWorkerId());
            dto.setWorkerName(attendance.getWorker().getFirstName() + " " + attendance.getWorker().getLastName());
            dto.setRemarks(attendance.getRemarks());
            dtos.add(dto);
        }
        return dtos;
    }

    public AttendanceEntity getAttendanceById(String id) {
        return attendanceDAO.findById(id);
    }

    public void updateAttendance(AttendanceEntity attendance) {
        attendanceDAO.update(attendance);
    }
}
