package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

/**
 * DeductionEntity
 *
 * Represents a financial deduction record for a worker.
 * Maps to the "deduction" table and stores amount, reason, and date.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "deduction")
public class DeductionEntity {
    /**
     * The unique identifier for the deduction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deduction_id")
    private Integer id;

    /**
     * The amount deducted from the worker's salary.
     */
    @Column(name = "amount", nullable = false)
    private Integer amount;

    /**
     * The reason for the deduction.
     */
    @Column(name = "reason", length = 500)
    private String reason;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private WorkerEntity worker;

    /**
     * Default constructor for DeductionEntity.
     * Initializes the date to current date.
     */
    public DeductionEntity() {
        this.date = new Date();
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
     * Gets the amount deducted.
     *
     * @return the amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the amount to be deducted.
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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the deduction.
     *
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the worker associated with this deduction.
     *
     * @return the worker entity
     */
    public WorkerEntity getWorker() {
        return worker;
    }

    /**
     * Sets the worker associated with this deduction.
     *
     * @param worker the worker entity to set
     */
    public void setWorker(WorkerEntity worker) {
        this.worker = worker;
    }
}
