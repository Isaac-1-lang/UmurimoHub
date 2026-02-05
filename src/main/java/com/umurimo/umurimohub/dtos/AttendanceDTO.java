package com.umurimo.umurimohub.dtos;

import java.util.Date;

/**
 * AttendanceDTO
 *
 * Data Transfer Object for Attendance records.
 * This class is responsible for transferring attendance data, including worker
 * details and status,
 * between the application layers.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
public class AttendanceDTO {
    private String id;
    private Date date;
    private String status;
    private String workerId;
    private String workerName;
    private String remarks;

    /**
     * Default constructor for AttendanceDTO.
     */
    public AttendanceDTO() {
    }

    /**
     * Gets the unique identifier for the attendance record.
     *
     * @return the attendance ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the attendance record.
     *
     * @param id the attendance ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the date of the attendance.
     *
     * @return the attendance date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the attendance.
     *
     * @param date the attendance date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the status of the attendance (e.g., PRESENT, ABSENT).
     *
     * @return the attendance status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the attendance.
     *
     * @param status the attendance status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the unique identifier of the worker.
     *
     * @return the worker ID
     */
    public String getWorkerId() {
        return workerId;
    }

    /**
     * Sets the unique identifier of the worker.
     *
     * @param workerId the worker ID to set
     */
    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    /**
     * Gets the name of the worker.
     *
     * @return the worker name
     */
    public String getWorkerName() {
        return workerName;
    }

    /**
     * Sets the name of the worker.
     *
     * @param workerName the worker name to set
     */
    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    /**
     * Gets any remarks or comments associated with the attendance.
     *
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets any remarks or comments associated with the attendance.
     *
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
