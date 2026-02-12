package com.umurimo.umurimohub.daos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.umurimo.umurimohub.entities.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class UserDAOTest {

  private UserDAO userDAO;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityTransaction transaction;

  @Mock
  private TypedQuery<UserEntity> query;

  @Mock
  private TypedQuery<Long> countQuery;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userDAO = spy(new UserDAO());
    doReturn(entityManager).when(userDAO).getEntityManager();
    when(entityManager.getTransaction()).thenReturn(transaction);
  }

  @Test
  public void testSave() {
    UserEntity user = new UserEntity();

    userDAO.save(user);

    verify(transaction).begin();
    verify(entityManager).persist(user);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testFindById() {
    UserEntity expectedUser = new UserEntity();
    when(entityManager.find(UserEntity.class, "1")).thenReturn(expectedUser);

    UserEntity result = userDAO.findById("1");

    assertNotNull(result);
    assertEquals(expectedUser, result);
    verify(entityManager).close();
  }

  @Test
  public void testFindByEmail() {
    UserEntity expectedUser = new UserEntity();
    when(entityManager.createQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
    when(query.getSingleResult()).thenReturn(expectedUser);

    UserEntity result = userDAO.findByEmail("test@example.com");

    assertNotNull(result);
    assertEquals(expectedUser, result);
    verify(entityManager).close();
  }

  @Test
  public void testFindAll() {
    when(entityManager.createQuery(anyString(), eq(UserEntity.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(Collections.emptyList());

    List<UserEntity> result = userDAO.findAll();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(entityManager).close();
  }

  @Test
  public void testUpdate() {
    UserEntity user = new UserEntity();

    userDAO.update(user);

    verify(transaction).begin();
    verify(entityManager).merge(user);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testDelete() {
    UserEntity user = new UserEntity();
    when(entityManager.find(UserEntity.class, "1")).thenReturn(user);

    userDAO.delete("1");

    verify(transaction).begin();
    verify(entityManager).remove(user);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testEmailExists() {
    when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(countQuery);
    when(countQuery.getSingleResult()).thenReturn(1L);

    boolean exists = userDAO.emailExists("test@example.com");

    assertTrue(exists);
    verify(entityManager).close();
  }
}
