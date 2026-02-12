package com.umurimo.umurimohub.dtos;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class AttendanceDTOTest {

  @Test
  public void testAttendanceDTOGettersAndSetters() {
    AttendanceDTO dto = new AttendanceDTO();

    String id = "att-1";
    dto.setId(id);
    assertEquals(id, dto.getId());

    Date date = new Date();
    dto.setDate(date);
    assertEquals(date, dto.getDate());

    String status = "PRESENT";
    dto.setStatus(status);
    assertEquals(status, dto.getStatus());

    String workerId = "w-1";
    dto.setWorkerId(workerId);
    assertEquals(workerId, dto.getWorkerId());

    String workerName = "John Doe";
    dto.setWorkerName(workerName);
    assertEquals(workerName, dto.getWorkerName());

    String remarks = "Good";
    dto.setRemarks(remarks);
    assertEquals(remarks, dto.getRemarks());
  }
}
