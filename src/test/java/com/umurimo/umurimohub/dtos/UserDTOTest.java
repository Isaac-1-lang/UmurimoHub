package com.umurimo.umurimohub.dtos;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

  @Test
  public void testUserDTOGettersAndSetters() {
    UserDTO dto = new UserDTO();

    String userId = "u-1";
    dto.setUserId(userId);
    assertEquals(userId, dto.getUserId());

    String firstName = "Admin";
    dto.setFirstName(firstName);
    assertEquals(firstName, dto.getFirstName());

    String lastName = "User";
    dto.setLastName(lastName);
    assertEquals(lastName, dto.getLastName());

    String email = "admin@example.com";
    dto.setEmail(email);
    assertEquals(email, dto.getEmail());

    String role = "CEO";
    dto.setRole(role);
    assertEquals(role, dto.getRole());

    String status = "ACTIVE";
    dto.setStatus(status);
    assertEquals(status, dto.getStatus());

    Date createdAt = new Date();
    dto.setCreatedAt(createdAt);
    assertEquals(createdAt, dto.getCreatedAt());

    Boolean passwordChanged = true;
    dto.setPasswordChanged(passwordChanged);
    assertTrue(dto.getPasswordChanged());
  }
}
