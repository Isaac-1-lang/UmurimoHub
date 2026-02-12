package com.umurimo.umurimohub.dtos;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PunishmentDTOTest {

  @Test
  public void testPunishmentDTOGettersAndSetters() {
    PunishmentDTO dto = new PunishmentDTO();

    String id = "p-1";
    dto.setId(id);
    assertEquals(id, dto.getId());

    String title = "Late";
    dto.setTitle(title);
    assertEquals(title, dto.getTitle());

    String description = "Came late 3 times";
    dto.setDescription(description);
    assertEquals(description, dto.getDescription());

    Date date = new Date();
    dto.setDate(date);
    assertEquals(date, dto.getDate());

    String workerId = "w-3";
    dto.setWorkerId(workerId);
    assertEquals(workerId, dto.getWorkerId());

    String workerName = "Bob";
    dto.setWorkerName(workerName);
    assertEquals(workerName, dto.getWorkerName());
  }
}
