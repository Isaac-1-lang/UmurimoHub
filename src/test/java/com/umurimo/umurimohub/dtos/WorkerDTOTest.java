package com.umurimo.umurimohub.dtos;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class WorkerDTOTest {

  @Test
  public void testWorkerDTOGettersAndSetters() {
    WorkerDTO dto = new WorkerDTO();

    String workerId = "w-100";
    dto.setWorkerId(workerId);
    assertEquals(workerId, dto.getWorkerId());

    String firstName = "Alice";
    dto.setFirstName(firstName);
    assertEquals(firstName, dto.getFirstName());

    String lastName = "Smith";
    dto.setLastName(lastName);
    assertEquals(lastName, dto.getLastName());

    String email = "alice@example.com";
    dto.setEmail(email);
    assertEquals(email, dto.getEmail());

    String phoneNumber = "987654321";
    dto.setPhoneNumber(phoneNumber);
    assertEquals(phoneNumber, dto.getPhoneNumber());

    Date hireDate = new Date();
    dto.setHireDate(hireDate);
    assertEquals(hireDate, dto.getHireDate());

    Integer baseSalary = 60000;
    dto.setBaseSalary(baseSalary);
    assertEquals(baseSalary, dto.getBaseSalary());

    String status = "ACTIVE";
    dto.setStatus(status);
    assertEquals(status, dto.getStatus());
  }
}
