package com.umurimo.umurimohub.entities;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.Assert.*;

public class PersonTest {
  @Test
  public void testPerson() {
    PersonEntity person = new PersonEntity();
    person.setId(1);
    person.setFirstName("John");
    person.setLastName("Doe");
    person.setEmail("john.doe@example.com");
    person.setGender("Male");
    person.setDob(LocalDate.parse("2000-12-20"));

    assertEquals(1, person.getId());
    assertEquals("John", person.getFirstName());
    assertEquals("Doe", person.getLastName());
    assertEquals("john.doe@example.com", person.getEmail());
//    assertEquals(Integer.valueOf(25), person.getAge());
    assertEquals("Male", person.getGender());
    assertTrue(person.getGender().equals("Male"));
    assertEquals(Integer.valueOf(25), person.getAge());



  }
}
