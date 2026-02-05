package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * WorkerEntity
 *
 * Represents a worker in the system.
 * This entity maps to the "workers" table in the database and stores personal,
 * employment, and related data (attendance, deductions, punishments).
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "workers")
public class WorkerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "worker_id")
    private String workerId;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "base_salary")
    private Integer baseSalary;

    @Column(name = "password", length = 100, nullable = false)
    private String password; // For worker login

    @Column(name = "status")
    private String status; // ACTIVE, INACTIVE

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<AttendanceEntity> attendances;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<DeductionEntity> deductions;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<PunishmentEntity> punishments;

    /**
     * Default constructor for WorkerEntity.
     * Initializes hire date and default status.
     */
    public WorkerEntity() {
        this.hireDate = new Date();
        this.status = "ACTIVE";
    }

    /**
     * Gets the unique identifier for the worker.
     *
     * @return the worker ID
     */
    public String getWorkerId() {
        return workerId;
    }

    /**
     * Sets the unique identifier for the worker.
     *
     * @param workerId the worker ID to set
     */
    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    /**
     * Gets the first name of the worker.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the worker.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the worker.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the worker.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the worker.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the worker.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the worker.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the worker.
     *
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the date when the worker was hired.
     *
     * @return the hire date
     */
    public Date getHireDate() {
        return hireDate;
    }

    /**
     * Sets the date when the worker was hired.
     *
     * @param hireDate the hire date to set
     */
    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * Gets the base salary of the worker.
     *
     * @return the base salary
     */
    public Integer getBaseSalary() {
        return baseSalary;
    }

    /**
     * Sets the base salary of the worker.
     *
     * @param baseSalary the base salary to set
     */
    public void setBaseSalary(Integer baseSalary) {
        this.baseSalary = baseSalary;
    }

    /**
     * Gets the encrypted password of the worker.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the encrypted password of the worker.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the employment status of the worker.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the employment status of the worker.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the list of attendance records for the worker.
     *
     * @return the list of attendance records
     */
    public List<AttendanceEntity> getAttendances() {
        return attendances;
    }

    /**
     * Sets the list of attendance records for the worker.
     *
     * @param attendances the list of attendance records to set
     */
    public void setAttendances(List<AttendanceEntity> attendances) {
        this.attendances = attendances;
    }

    /**
     * Gets the list of deduction records for the worker.
     *
     * @return the list of deduction records
     */
    public List<DeductionEntity> getDeductions() {
        return deductions;
    }

    /**
     * Sets the list of deduction records for the worker.
     *
     * @param deductions the list of deduction records to set
     */
    public void setDeductions(List<DeductionEntity> deductions) {
        this.deductions = deductions;
    }

    /**
     * Gets the list of punishment records for the worker.
     *
     * @return the list of punishment records
     */
    public List<PunishmentEntity> getPunishments() {
        return punishments;
    }

    /**
     * Sets the list of punishment records for the worker.
     *
     * @param punishments the list of punishment records to set
     */
    public void setPunishments(List<PunishmentEntity> punishments) {
        this.punishments = punishments;
    }
}
