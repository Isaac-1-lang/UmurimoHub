package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

/**
 * PunishmentEntity
 *
 * Represents a disciplinary action or punishment record for a worker.
 * Maps to the "punishment" table and stores details about the infraction and
 * date.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "punishment")
public class PunishmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "punishment_id")
    private String id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private WorkerEntity worker;

    /**
     * Default constructor for PunishmentEntity.
     * Initializes the date to current date.
     */
    public PunishmentEntity() {
        this.date = new Date();
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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the punishment.
     *
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the worker receiving the punishment.
     *
     * @return the worker entity
     */
    public WorkerEntity getWorker() {
        return worker;
    }

    /**
     * Sets the worker receiving the punishment.
     *
     * @param worker the worker entity to set
     */
    public void setWorker(WorkerEntity worker) {
        this.worker = worker;
    }
}
