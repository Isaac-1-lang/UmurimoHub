package com.umurimo.umurimohub.dtos;

import java.util.Date;

/**
 * PunishmentDTO
 *
 * Data Transfer Object for Punishment records.
 * This class is responsible for transferring punishment details, including
 * title and description,
 * between the application layers.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
public class PunishmentDTO {
    private String id;
    private String title;
    private String description;
    private Date date;
    private String workerId;
    private String workerName;

    /**
     * Default constructor for PunishmentDTO.
     */
    public PunishmentDTO() {
    }

    /**
     * Gets the unique identifier for the punishment.
     *
     * @return the punishment ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the punishment.
     *
     * @param id the punishment ID to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of the punishment.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the punishment.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the punishment.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the punishment.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the date of the punishment.
     *
     * @return the punishment date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the punishment.
     *
     * @param date the punishment date to set
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
