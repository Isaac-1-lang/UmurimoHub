package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "worker")
public class WorkerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String workerId;
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    @Column(length = 100)
    private String email;
    @Column
    private String phoneNumber;
    @Column
    private Date hireDate;
    @Column
    private Integer baseSalary;
}
