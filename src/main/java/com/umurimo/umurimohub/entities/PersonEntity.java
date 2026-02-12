package com.umurimo.umurimohub.entities;

import java.time.LocalDate;
import java.time.Period;

public class PersonEntity {
  private int Id;
  private String firstName;
  private String lastName;
  private String email;
  private String gender;
  public LocalDate dob;

  public PersonEntity() {
  }

  public PersonEntity(int id, String firstName, String lastName, String email, Integer age, String gender) {
    this.Id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.gender = gender;
  }

  public PersonEntity(int id, String firstName, String lastName, String email,String gender, LocalDate dob) {
    Id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.gender = gender;
    this.dob = dob;
  }


  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public LocalDate getDob() {
    return this.dob;
  }



  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
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

  public Integer getAge() {
    return Period.between(dob, LocalDate.now()).getYears();
  }
  public Integer getAgeInYears() {
    return LocalDate.now().getYear() - getDob().getYear();
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

}
