package com.umurimo.umurimohub.entities;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "punishment")
public class PunishmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Date date;
}
