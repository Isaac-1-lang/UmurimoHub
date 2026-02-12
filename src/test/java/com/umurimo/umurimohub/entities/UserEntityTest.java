package com.umurimo.umurimohub.entities;

import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {

  @Test
  public void testUserEntityGettersAndSetters() {
    UserEntity user = new UserEntity();

    // Test ID
    String userId = "user-uuid";
    user.setUserId(userId);
    assertEquals(userId, user.getUserId());

    // Test Name
    user.setFirstName("Jane");
    user.setLastName("Doe");
    assertEquals("Jane", user.getFirstName());
    assertEquals("Doe", user.getLastName());

    // Test Email
    String email = "jane@example.com";
    user.setEmail(email);
    assertEquals(email, user.getEmail());

    // Test Password
    String password = "hashedpassword";
    user.setPassword(password);
    assertEquals(password, user.getPassword());

    // Test Role
    String role = "HR";
    user.setRole(role);
    assertEquals(role, user.getRole());

    // Test Status
    String status = "INACTIVE";
    user.setStatus(status);
    assertEquals(status, user.getStatus());

    // Test CreatedAt
    Date createdAt = new Date();
    user.setCreatedAt(createdAt);
    assertEquals(createdAt, user.getCreatedAt());

    // Test Password Changed
    user.setPasswordChanged(true);
    assertTrue(user.getPasswordChanged());

    // Test Activity Logs
    List<HRActivityLog> logs = new ArrayList<>();
    user.setActivityLogs(logs);
    assertEquals(logs, user.getActivityLogs());
  }

  @Test
  public void testConstructor() {
    UserEntity user = new UserEntity();
    assertNotNull(user.getCreatedAt());
    assertEquals("ACTIVE", user.getStatus());
    assertFalse(user.getPasswordChanged());
  }
}
