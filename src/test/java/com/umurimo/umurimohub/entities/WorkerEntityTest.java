package com.umurimo.umurimohub.entities;

import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class WorkerEntityTest {

  @Test
  public void testWorkerEntityGettersAndSetters() {
    WorkerEntity worker = new WorkerEntity();

    // Test ID
    String workerId = "worker-uuid";
    worker.setWorkerId(workerId);
    assertEquals(workerId, worker.getWorkerId());

    // Test Name
    worker.setFirstName("Bob");
    worker.setLastName("Builder");
    assertEquals("Bob", worker.getFirstName());
    assertEquals("Builder", worker.getLastName());

    // Test Email
    String email = "bob@example.com";
    worker.setEmail(email);
    assertEquals(email, worker.getEmail());

    // Test Phone
    String phone = "1234567890";
    worker.setPhoneNumber(phone);
    assertEquals(phone, worker.getPhoneNumber());

    // Test Hire Date
    Date hireDate = new Date();
    worker.setHireDate(hireDate);
    assertEquals(hireDate, worker.getHireDate());

    // Test Salary
    Integer salary = 50000;
    worker.setBaseSalary(salary);
    assertEquals(salary, worker.getBaseSalary());

    // Test Password
    String password = "securepass";
    worker.setPassword(password);
    assertEquals(password, worker.getPassword());

    // Test Status
    String status = "INACTIVE";
    worker.setStatus(status);
    assertEquals(status, worker.getStatus());

    // Test Lists
    List<AttendanceEntity> attendances = new ArrayList<>();
    worker.setAttendances(attendances);
    assertEquals(attendances, worker.getAttendances());

    List<DeductionEntity> deductions = new ArrayList<>();
    worker.setDeductions(deductions);
    assertEquals(deductions, worker.getDeductions());

    List<PunishmentEntity> punishments = new ArrayList<>();
    worker.setPunishments(punishments);
    assertEquals(punishments, worker.getPunishments());
  }

  @Test
  public void testConstructor() {
    WorkerEntity worker = new WorkerEntity();
    assertNotNull(worker.getHireDate());
    assertEquals("ACTIVE", worker.getStatus());
  }
}
