package com.umurimo.umurimohub.dtos;

import java.util.Date;

/**
 * WorkerDTO
 *
 * Data Transfer Object for Worker information.
 * This class is responsible for transferring worker details, including personal
 * info, salary, and status,
 * between the application layers.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
public class WorkerDTO {
    private String workerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date hireDate;
    private Integer baseSalary;
    private String status;

    /**
     * Default constructor for WorkerDTO.
     */
    public WorkerDTO() {
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
}
