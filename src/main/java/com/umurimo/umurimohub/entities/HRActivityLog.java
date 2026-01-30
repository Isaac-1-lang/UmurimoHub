package com.umurimo.umurimohub.entities;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "hrActivity")
public class HRActivityLog {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Column
  private String action;
  @Column
    private Date timestamp;

}
