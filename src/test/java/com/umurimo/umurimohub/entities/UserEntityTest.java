package com.umurimo.umurimohub.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class UserEntityTest {

  private UserEntity user;

  @BeforeEach
  void setUp() {
    user = new UserEntity();
  }

  @Test
  void testDefaultConstructor() {
    assertNotNull(user.getCreatedAt(), "CreatedAt should be initialized");
    assertEquals("ACTIVE", user.getStatus(), "Default status should be ACTIVE");
    assertFalse(user.getPasswordChanged(), "PasswordChanged should be false by default");
  }

  @Test
  void testSetAndGetUserId() {
    String id = "uuid-123";
    user.setUserId(id);
    assertEquals(id, user.getUserId());
  }

  @Test
  void testSetAndGetEmail() {
    String email = "test@example.com";
    user.setEmail(email);
    assertEquals(email, user.getEmail());
  }

  @Test
  void testSetAndGetRole() {
    String role = "HR";
    user.setRole(role);
    assertEquals(role, user.getRole());
  }

  @Test
  void testSetAndGetActivityLogs() {
    ArrayList<HRActivityLog> logs = new ArrayList<>();
    user.setActivityLogs(logs);
    assertSame(logs, user.getActivityLogs());
  }
}
