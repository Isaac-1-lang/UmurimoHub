package com.umurimo.umurimohub.entities;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PunishmentEntityTest {

  @Test
  public void testPunishmentEntityGettersAndSetters() {
    PunishmentEntity punishment = new PunishmentEntity();

    // Test ID
    String id = "punishment-uuid";
    punishment.setId(id);
    assertEquals(id, punishment.getId());

    // Test Title
    String title = "Violation 1";
    punishment.setTitle(title);
    assertEquals(title, punishment.getTitle());

    // Test Description
    String description = "Details about violation";
    punishment.setDescription(description);
    assertEquals(description, punishment.getDescription());

    // Test Date
    Date date = new Date();
    punishment.setDate(date);
    assertEquals(date, punishment.getDate());

    // Test Worker
    WorkerEntity worker = new WorkerEntity();
    punishment.setWorker(worker);
    assertEquals(worker, punishment.getWorker());
  }

  @Test
  public void testConstructor() {
    PunishmentEntity punishment = new PunishmentEntity();
    assertNotNull(punishment.getDate());
  }
}
