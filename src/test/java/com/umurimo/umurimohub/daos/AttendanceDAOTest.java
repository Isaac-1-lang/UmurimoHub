package com.umurimo.umurimohub.daos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.umurimo.umurimohub.entities.AttendanceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class AttendanceDAOTest {

  private AttendanceDAO attendanceDAO;

  @Mock
  private EntityManager entityManager;

  @Mock
  private EntityTransaction transaction;

  @Mock
  private TypedQuery<AttendanceEntity> query;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    attendanceDAO = spy(new AttendanceDAO());
    doReturn(entityManager).when(attendanceDAO).getEntityManager();
    when(entityManager.getTransaction()).thenReturn(transaction);
  }

  @Test
  public void testSave() {
    AttendanceEntity attendance = new AttendanceEntity();

    attendanceDAO.save(attendance);

    verify(transaction).begin();
    verify(entityManager).persist(attendance);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testFindById() {
    AttendanceEntity expectedAttendance = new AttendanceEntity();
    when(entityManager.find(AttendanceEntity.class, "1")).thenReturn(expectedAttendance);

    AttendanceEntity result = attendanceDAO.findById("1");

    assertNotNull(result);
    assertEquals(expectedAttendance, result);
    verify(entityManager).close();
  }

  @Test
  public void testFindAll() {
    when(entityManager.createQuery(anyString(), eq(AttendanceEntity.class))).thenReturn(query);
    when(query.getResultList()).thenReturn(Collections.emptyList());

    List<AttendanceEntity> result = attendanceDAO.findAll();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(entityManager).close();
  }

  @Test
  public void testUpdate() {
    AttendanceEntity attendance = new AttendanceEntity();

    attendanceDAO.update(attendance);

    verify(transaction).begin();
    verify(entityManager).merge(attendance);
    verify(transaction).commit();
    verify(entityManager).close();
  }

  @Test
  public void testDelete() {
    AttendanceEntity attendance = new AttendanceEntity();
    when(entityManager.find(AttendanceEntity.class, "1")).thenReturn(attendance);

    attendanceDAO.delete("1");

    verify(transaction).begin();
    verify(entityManager).remove(attendance);
    verify(transaction).commit();
    verify(entityManager).close();
  }
}
