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

import com.umurimo.umurimohub.entities.PunishmentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PunishmentDAOTest {

  private PunishmentDAO punishmentDAO;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityTransaction transaction;

  @Mock
  private TypedQuery<PunishmentEntity> query;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    punishmentDAO = spy(new PunishmentDAO());
    doReturn(entityManager).when(punishmentDAO).getEntityManager();
    when(entityManager.getTransaction()).thenReturn(transaction);
  }

  @Test
  public void testSave() {
    PunishmentEntity punishment = new PunishmentEntity();

    punishmentDAO.save(punishment);

    verify(transaction).begin();
    verify(entityManager).persist(punishment);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testFindById() {
    PunishmentEntity expectedPunishment = new PunishmentEntity();
    when(entityManager.find(PunishmentEntity.class, "1")).thenReturn(expectedPunishment);

    PunishmentEntity result = punishmentDAO.findById("1");

    assertNotNull(result);
    assertEquals(expectedPunishment, result);
    verify(entityManager).close();
  }

  @Test
  public void testFindAll() {
    when(entityManager.createQuery(anyString(), eq(PunishmentEntity.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(Collections.emptyList());

    List<PunishmentEntity> result = punishmentDAO.findAll();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(entityManager).close();
  }

  @Test
  public void testUpdate() {
    PunishmentEntity punishment = new PunishmentEntity();

    punishmentDAO.update(punishment);

    verify(transaction).begin();
    verify(entityManager).merge(punishment);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testDelete() {
    PunishmentEntity punishment = new PunishmentEntity();
    when(entityManager.find(PunishmentEntity.class, "1")).thenReturn(punishment);

    punishmentDAO.delete("1");

    verify(transaction).begin();
    verify(entityManager).remove(punishment);
    verify(transaction).commit();
    verify(entityManager).close();
  }
}
