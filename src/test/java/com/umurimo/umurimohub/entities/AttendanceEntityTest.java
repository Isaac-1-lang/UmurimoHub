package com.umurimo.umurimohub.entities;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class AttendanceEntityTest {

  @Test
  public void testAttendanceEntityGettersAndSetters() {
    AttendanceEntity attendance = new AttendanceEntity();

    // Test ID
    String id = "test-uuid";
    attendance.setId(id);
    assertEquals(id, attendance.getId());

    // Test Date (Constructor initializes it, but we can set it too)
    Date date = new Date();
    attendance.setDate(date);
    assertEquals(date, attendance.getDate());

    // Test Status
    String status = "PRESENT";
    attendance.setStatus(status);
    assertEquals(status, attendance.getStatus());

    // Test Remarks
    String remarks = "On time";
    attendance.setRemarks(remarks);
    assertEquals(remarks, attendance.getRemarks());

    // Test Worker
    WorkerEntity worker = new WorkerEntity();
    attendance.setWorker(worker);
    assertEquals(worker, attendance.getWorker());
  }

  @Test
  public void testConstructor() {
    AttendanceEntity attendance = new AttendanceEntity();
    assertNotNull(attendance.getDate());
  }
}
