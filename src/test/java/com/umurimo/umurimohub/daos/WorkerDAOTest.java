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

import com.umurimo.umurimohub.entities.WorkerEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class WorkerDAOTest {

  private WorkerDAO workerDAO;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityTransaction transaction;

  @Mock
  private TypedQuery<WorkerEntity> query;

  @Mock
  private TypedQuery<Long> countQuery;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    workerDAO = spy(new WorkerDAO());
    doReturn(entityManager).when(workerDAO).getEntityManager();
    when(entityManager.getTransaction()).thenReturn(transaction);
  }

  @Test
  public void testSave() {
    WorkerEntity worker = new WorkerEntity();

    workerDAO.save(worker);

    verify(transaction).begin();
    verify(entityManager).persist(worker);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testFindById() {
    WorkerEntity expectedWorker = new WorkerEntity();
    when(entityManager.find(WorkerEntity.class, "1")).thenReturn(expectedWorker);

    WorkerEntity result = workerDAO.findById("1");

    assertNotNull(result);
    assertEquals(expectedWorker, result);
    verify(entityManager).close();
  }

  @Test
  public void testFindByEmail() {
    WorkerEntity expectedWorker = new WorkerEntity();
    when(entityManager.createQuery(anyString(), eq(WorkerEntity.class))).thenReturn(query);
    when(query.getSingleResult()).thenReturn(expectedWorker);

    WorkerEntity result = workerDAO.findByEmail("test@example.com");

    assertNotNull(result);
    assertEquals(expectedWorker, result);
    verify(entityManager).close();
  }

  @Test
  public void testFindAll() {
    when(entityManager.createQuery(anyString(), eq(WorkerEntity.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(Collections.emptyList());

    List<WorkerEntity> result = workerDAO.findAll();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(entityManager).close();
  }

  @Test
  public void testUpdate() {
    WorkerEntity worker = new WorkerEntity();

    workerDAO.update(worker);

    verify(transaction).begin();
    verify(entityManager).merge(worker);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testDelete() {
    WorkerEntity worker = new WorkerEntity();
    when(entityManager.find(WorkerEntity.class, "1")).thenReturn(worker);

    workerDAO.delete("1");

    verify(transaction).begin();
    verify(entityManager).remove(worker);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testEmailExists() {
    when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(countQuery);
    when(countQuery.getSingleResult()).thenReturn(1L);

    boolean exists = workerDAO.emailExists("test@example.com");

    assertTrue(exists);
    verify(entityManager).close();
  }
}
