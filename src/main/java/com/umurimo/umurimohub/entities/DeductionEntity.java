package com.umurimo.umurimohub.entities;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "deduction")
public class DeductionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer amount;
    @Column(length = 100)
    private String reason;
    @Column
    private Date date;

}
