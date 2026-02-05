package com.umurimo.umurimohub.dtos;

import java.util.Date;

/**
 * DeductionDTO
 *
 * Data Transfer Object for Deduction records.
 * This class is responsible for transferring deduction details, including
 * amount and reason,
 * between the application layers.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
public class DeductionDTO {
    private Integer id;
    private Integer amount;
    private String reason;
    private Date date;
    private String workerId;
    private String workerName;

    /**
     * Default constructor for DeductionDTO.
     */
    public DeductionDTO() {
    }

    /**
     * Gets the unique identifier for the deduction.
     *
     * @return the deduction ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the deduction.
     *
     * @param id the deduction ID to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the deduction amount.
     *
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the deduction amount.
     *
     * @param amount the amount to set
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Gets the reason for the deduction.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason for the deduction.
     *
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets the date of the deduction.
     *
     * @return the deduction date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the deduction.
     *
     * @param date the deduction date to set
     */
    public void setDate(Date date) {
        this.date = date;
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
}
