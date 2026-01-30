package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(length = 100,nullable = false,unique = true)
    private String email;
    @Column(length = 100,nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    @Column
    private String status;
    @Column
    private Date createdAt;
}
