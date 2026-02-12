package com.umurimo.umurimohub.dtos;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class DeductionDTOTest {

  @Test
  public void testDeductionDTOGettersAndSetters() {
    DeductionDTO dto = new DeductionDTO();

    Integer id = 100;
    dto.setId(id);
    assertEquals(id, dto.getId());

    Integer amount = 200;
    dto.setAmount(amount);
    assertEquals(amount, dto.getAmount());

    String reason = "Damaged equipment";
    dto.setReason(reason);
    assertEquals(reason, dto.getReason());

    Date date = new Date();
    dto.setDate(date);
    assertEquals(date, dto.getDate());

    String workerId = "w-2";
    dto.setWorkerId(workerId);
    assertEquals(workerId, dto.getWorkerId());

    String workerName = "Jane Doe";
    dto.setWorkerName(workerName);
    assertEquals(workerName, dto.getWorkerName());
  }
}
