package com.umurimo.umurimohub.entities;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class HRActivityLogTest {

  @Test
  public void testHRActivityLogGettersAndSetters() {
    HRActivityLog log = new HRActivityLog();

    // Test ID
    String id = "log-uuid";
    log.setId(id);
    assertEquals(id, log.getId());

    // Test Action
    String action = "Created User";
    log.setAction(action);
    assertEquals(action, log.getAction());

    // Test Timestamp
    Date timestamp = new Date();
    log.setTimestamp(timestamp);
    assertEquals(timestamp, log.getTimestamp());

    // Test Details
    String details = "User John Doe created.";
    log.setDetails(details);
    assertEquals(details, log.getDetails());

    // Test HR User
    UserEntity hrUser = new UserEntity();
    log.setHrUser(hrUser);
    assertEquals(hrUser, log.getHrUser());
  }

  @Test
  public void testConstructor() {
    HRActivityLog log = new HRActivityLog();
    assertNotNull(log.getTimestamp());
  }
}
