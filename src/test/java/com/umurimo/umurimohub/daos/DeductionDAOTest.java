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

import com.umurimo.umurimohub.entities.DeductionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class DeductionDAOTest {

  private DeductionDAO deductionDAO;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityTransaction transaction;

  @Mock
  private TypedQuery<DeductionEntity> query;

  @Mock
  private TypedQuery<Long> sumQuery;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    deductionDAO = spy(new DeductionDAO());
    doReturn(entityManager).when(deductionDAO).getEntityManager();
    when(entityManager.getTransaction()).thenReturn(transaction);
  }

  @Test
  public void testSave() {
    DeductionEntity deduction = new DeductionEntity();

    deductionDAO.save(deduction);

    verify(transaction).begin();
    verify(entityManager).persist(deduction);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testFindById() {
    DeductionEntity expectedDeduction = new DeductionEntity();
    when(entityManager.find(DeductionEntity.class, 1)).thenReturn(expectedDeduction);

    DeductionEntity result = deductionDAO.findById(1);

    assertNotNull(result);
    assertEquals(expectedDeduction, result);
    verify(entityManager).close();
  }

  @Test
  public void testFindAll() {
    when(entityManager.createQuery(anyString(), eq(DeductionEntity.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(Collections.emptyList());

    List<DeductionEntity> result = deductionDAO.findAll();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(entityManager).close();
  }

  @Test
  public void testUpdate() {
    DeductionEntity deduction = new DeductionEntity();

    deductionDAO.update(deduction);

    verify(transaction).begin();
    verify(entityManager).merge(deduction);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testDelete() {
    DeductionEntity deduction = new DeductionEntity();
    when(entityManager.find(DeductionEntity.class, 1)).thenReturn(deduction);

    deductionDAO.delete(1);

    verify(transaction).begin();
    verify(entityManager).remove(deduction);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testGetTotalDeductionsByWorker() {
    when(entityManager.createQuery(anyString(), eq(Long.class))).thenReturn(sumQuery);
    when(sumQuery.getSingleResult()).thenReturn(100L);

    Integer total = deductionDAO.getTotalDeductionsByWorker("worker1");

    assertEquals(100, total);
    verify(entityManager).close();
  }
}
