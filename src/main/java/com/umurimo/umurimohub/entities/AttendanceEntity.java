package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="attendance")
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private Date date;
    @Column
    private String status;
}
