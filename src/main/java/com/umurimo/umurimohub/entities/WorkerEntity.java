package com.umurimo.umurimohub.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "workers")
public class WorkerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "worker_id")
    private String workerId;
    
    @Column(name = "first_name", length = 100)
    private String firstName;
    
    @Column(name = "last_name", length = 100)
    private String lastName;
    
    @Column(name = "email", length = 100, unique = true)
    private String email;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "hire_date")
    private Date hireDate;
    
    @Column(name = "base_salary")
    private Integer baseSalary;
    
    @Column(name = "password", length = 100, nullable = false)
    private String password; // For worker login
    
    @Column(name = "status")
    private String status; // ACTIVE, INACTIVE
    
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<AttendanceEntity> attendances;
    
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<DeductionEntity> deductions;
    
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    private List<PunishmentEntity> punishments;

    // Constructors
    public WorkerEntity() {
        this.hireDate = new Date();
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Integer baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AttendanceEntity> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceEntity> attendances) {
        this.attendances = attendances;
    }

    public List<DeductionEntity> getDeductions() {
        return deductions;
    }

    public void setDeductions(List<DeductionEntity> deductions) {
        this.deductions = deductions;
    }

    public List<PunishmentEntity> getPunishments() {
        return punishments;
    }

    public void setPunishments(List<PunishmentEntity> punishments) {
        this.punishments = punishments;
    }
}
