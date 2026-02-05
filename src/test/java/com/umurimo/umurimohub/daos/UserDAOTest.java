package com.umurimo.umurimohub.daos;

import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.utils.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

  private UserDAO userDAO;

  @BeforeAll
  static void setupDatabase() {
    // Configure H2 Database for testing
    Map<String, String> testProperties = new HashMap<>();
    testProperties.put("jakarta.persistence.jdbc.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
    testProperties.put("jakarta.persistence.jdbc.user", "sa");
    testProperties.put("jakarta.persistence.jdbc.password", "");
    testProperties.put("jakarta.persistence.jdbc.driver", "org.h2.Driver");
    testProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    testProperties.put("hibernate.hbm2ddl.auto", "create-drop");

    // Initialize DBConnection with test overrides
    DBConnection.initialize(testProperties);
  }

  @BeforeEach
  void setUp() {
    userDAO = new UserDAO();
  }

  @AfterEach
  void tearDown() {
    // ideally truncate tables or rely on create-drop if we re-init every time
    // (expensive)
    // or just manually clean up specific test data
    List<UserEntity> users = userDAO.findAll();
    for (UserEntity u : users) {
      userDAO.delete(u.getUserId());
    }
  }

  @Test
  void testSaveAndFindById() {
    UserEntity user = new UserEntity();
    user.setEmail("save@test.com");
    user.setPassword("password123");
    user.setRole("HR");
    user.setFirstName("Test");
    user.setLastName("User");

    userDAO.save(user);

    assertNotNull(user.getUserId(), "User ID should be generated");

    UserEntity found = userDAO.findById(user.getUserId());
    assertNotNull(found);
    assertEquals("save@test.com", found.getEmail());
  }

  @Test
  void testFindByEmail() {
    UserEntity user = new UserEntity();
    user.setEmail("find@test.com");
    user.setPassword("pass");
    user.setRole("CEO");

    userDAO.save(user);

    UserEntity found = userDAO.findByEmail("find@test.com");
    assertNotNull(found);
    assertEquals("CEO", found.getRole());

    assertNull(userDAO.findByEmail("nonexistent@test.com"));
  }

  @Test
  void testEmailExists() {
    UserEntity user = new UserEntity();
    user.setEmail("exists@test.com");
    user.setPassword("pass");
    user.setRole("HR");

    userDAO.save(user);

    assertTrue(userDAO.emailExists("exists@test.com"));
    assertFalse(userDAO.emailExists("other@test.com"));
  }

  @Test
  void testFindAll() {
    UserEntity u1 = new UserEntity();
    u1.setEmail("u1@test.com");
    u1.setPassword("p");
    u1.setRole("HR");
    userDAO.save(u1);

    UserEntity u2 = new UserEntity();
    u2.setEmail("u2@test.com");
    u2.setPassword("p");
    u2.setRole("CEO");
    userDAO.save(u2);

    List<UserEntity> all = userDAO.findAll();
    assertTrue(all.size() >= 2);
  }
}
