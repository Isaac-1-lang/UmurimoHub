package com.umurimo.umurimohub.entities;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class DeductionEntityTest {

  @Test
  public void testDeductionEntityGettersAndSetters() {
    DeductionEntity deduction = new DeductionEntity();

    // Test ID
    Integer id = 1;
    deduction.setId(id);
    assertEquals(id, deduction.getId());

    // Test Amount
    Integer amount = 500;
    deduction.setAmount(amount);
    assertEquals(amount, deduction.getAmount());

    // Test Reason
    String reason = "Late arrival";
    deduction.setReason(reason);
    assertEquals(reason, deduction.getReason());

    // Test Date
    Date date = new Date();
    deduction.setDate(date);
    assertEquals(date, deduction.getDate());

    // Test Worker
    WorkerEntity worker = new WorkerEntity();
    deduction.setWorker(worker);
    assertEquals(worker, deduction.getWorker());
  }

  @Test
  public void testConstructor() {
    DeductionEntity deduction = new DeductionEntity();
    assertNotNull(deduction.getDate());
  }
}
