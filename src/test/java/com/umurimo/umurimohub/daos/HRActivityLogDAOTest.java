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

import com.umurimo.umurimohub.entities.HRActivityLog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class HRActivityLogDAOTest {

  private HRActivityLogDAO activityLogDAO;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityTransaction transaction;

  @Mock
  private TypedQuery<HRActivityLog> query;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    activityLogDAO = spy(new HRActivityLogDAO());
    doReturn(entityManager).when(activityLogDAO).getEntityManager();
    when(entityManager.getTransaction()).thenReturn(transaction);
  }

  @Test
  public void testSave() {
    HRActivityLog log = new HRActivityLog();

    activityLogDAO.save(log);

    verify(transaction).begin();
    verify(entityManager).persist(log);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testFindById() {
    HRActivityLog expectedLog = new HRActivityLog();
    when(entityManager.find(HRActivityLog.class, "1")).thenReturn(expectedLog);

    HRActivityLog result = activityLogDAO.findById("1");

    assertNotNull(result);
    assertEquals(expectedLog, result);
    verify(entityManager).close();
  }

  @Test
  public void testFindAll() {
    when(entityManager.createQuery(anyString(), eq(HRActivityLog.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(Collections.emptyList());

    List<HRActivityLog> result = activityLogDAO.findAll();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(entityManager).close();
  }
}
