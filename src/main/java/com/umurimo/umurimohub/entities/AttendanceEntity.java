package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

/**
 * AttendanceEntity
 *
 * Represents an attendance record for a worker.
 * Maps to the "attendance" table and tracks daily attendance status (e.g.,
 * PRESENT, ABSENT).
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 * {@link WorkerEntity} the worker entity
 */
@Entity
@Table(name = "attendance")
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attendance_id")
    private String id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "status", nullable = false)
    private String status; // PRESENT, ABSENT, LATE, LEAVE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private WorkerEntity worker;

    @Column(name = "remarks")
    private String remarks;

    /**
     * Default constructor for AttendanceEntity.
     * Initializes the date to current date.
     */
    public AttendanceEntity() {
        this.date = new Date();
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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the attendance.
     *
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the status of the attendance.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the attendance.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the worker associated with this attendance record.
     *
     * @return the worker entity
     */
    public WorkerEntity getWorker() {
        return worker;
    }

    /**
     * Sets the worker associated with this attendance record.
     *
     * @param worker the worker entity to set
     */
    public void setWorker(WorkerEntity worker) {
        this.worker = worker;
    }

    /**
     * Gets any remarks regarding the attendance.
     *
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets any remarks regarding the attendance.
     *
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
