package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.*;
import com.umurimo.umurimohub.dtos.*;
import com.umurimo.umurimohub.entities.HRActivityLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    private WorkerDAO workerDAO = new WorkerDAO();
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private HRActivityLogDAO hrActivityLogDAO = new HRActivityLogDAO();
    private UserDAO userDAO = new UserDAO();

    public Map<String, Object> getCEOReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Total workers
        long totalWorkers = workerDAO.findAll().size();
        report.put("totalWorkers", totalWorkers);
        
        // Total active workers
        long activeWorkers = workerDAO.findByStatus("ACTIVE").size();
        report.put("activeWorkers", activeWorkers);
        
        // Total HR users
        long totalHR = userDAO.findByRole("HR").size();
        report.put("totalHR", totalHR);
        
        // Total attendance records
        long totalAttendance = attendanceDAO.findAll().size();
        report.put("totalAttendance", totalAttendance);
        
        // Total deductions
        List<DeductionDTO> allDeductions = new DeductionService().getAllDeductions();
        int totalDeductionAmount = allDeductions.stream()
            .mapToInt(DeductionDTO::getAmount)
            .sum();
        report.put("totalDeductionAmount", totalDeductionAmount);
        report.put("totalDeductionCount", allDeductions.size());
        
        // Total punishments
        long totalPunishments = new PunishmentService().getAllPunishments().size();
        report.put("totalPunishments", totalPunishments);
        
        // HR Activity Logs
        List<HRActivityLog> activityLogs = hrActivityLogDAO.findAll();
        report.put("totalHRActivities", activityLogs.size());
        
        // Recent HR activities (last 10)
        List<Map<String, Object>> recentActivities = new ArrayList<>();
        int count = 0;
        for (HRActivityLog log : activityLogs) {
            if (count >= 10) break;
            Map<String, Object> activity = new HashMap<>();
            activity.put("action", log.getAction());
            activity.put("timestamp", log.getTimestamp());
            activity.put("hrName", log.getHrUser().getFirstName() + " " + log.getHrUser().getLastName());
            activity.put("details", log.getDetails());
            recentActivities.add(activity);
            count++;
        }
        report.put("recentHRActivities", recentActivities);
        
        return report;
    }

    public List<WorkerDTO> getWorkerSummary() {
        WorkerService workerService = new WorkerService();
        return workerService.getAllWorkers();
    }

    public List<AttendanceDTO> getAttendanceSummary() {
        AttendaceService attendanceService = new AttendaceService();
        return attendanceService.getAllAttendance();
    }

    public List<DeductionDTO> getDeductionSummary() {
        DeductionService deductionService = new DeductionService();
        return deductionService.getAllDeductions();
    }

    public List<Map<String, Object>> getHRActivitySummary() {
        List<HRActivityLog> logs = hrActivityLogDAO.findAll();
        List<Map<String, Object>> summary = new ArrayList<>();
        
        for (HRActivityLog log : logs) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("id", log.getId());
            entry.put("action", log.getAction());
            entry.put("timestamp", log.getTimestamp());
            entry.put("hrName", log.getHrUser().getFirstName() + " " + log.getHrUser().getLastName());
            entry.put("hrEmail", log.getHrUser().getEmail());
            entry.put("details", log.getDetails());
            summary.add(entry);
        }
        
        return summary;
    }
}
