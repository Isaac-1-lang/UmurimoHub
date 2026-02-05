package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.AttendanceDAO;
import com.umurimo.umurimohub.daos.WorkerDAO;
import com.umurimo.umurimohub.dtos.AttendanceDTO;
import com.umurimo.umurimohub.entities.AttendanceEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AttendaceService
 *
 * Service implementation for managing worker attendance.
 * Provides functionality for creating attendance records, retrieving attendance
 * history
 * (by worker, by date range), and managing attendance data.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class AttendaceService {
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private WorkerDAO workerDAO = new WorkerDAO();

    /**
     * Creates a new attendance record for a specific worker.
     *
     * @param workerId the ID of the worker
     * @param date     the date of attendance
     * @param status   the attendance status (e.g., "PRESENT", "ABSENT")
     * @param remarks  any additional remarks
     * @return the created AttendanceEntity
     * @throws RuntimeException if the worker is not found
     */
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

    /**
     * Retrieves attendance history for a specific worker.
     *
     * @param workerId the ID of the worker
     * @return a list of AttendanceDTOs containing attendance details
     */
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

    /**
     * Retrieves all attendance records in the system.
     *
     * @return a list of all AttendanceDTOs
     */
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

    /**
     * Retrieves attendance records within a specific date range.
     *
     * @param startDate the start date (inclusive)
     * @param endDate   the end date (inclusive)
     * @return a list of AttendanceDTOs within the date range
     */
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

    /**
     * Retrieves a specific attendance record by its ID.
     *
     * @param id the attendance ID
     * @return the AttendanceEntity, or null if not found
     */
    public AttendanceEntity getAttendanceById(String id) {
        return attendanceDAO.findById(id);
    }

    /**
     * Updates an existing attendance record.
     *
     * @param attendance the attendance entity with updated values
     */
    public void updateAttendance(AttendanceEntity attendance) {
        attendanceDAO.update(attendance);
    }
}
