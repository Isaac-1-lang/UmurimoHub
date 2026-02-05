package com.umurimo.umurimohub.services;

import com.umurimo.umurimohub.daos.*;
import com.umurimo.umurimohub.dtos.*;
import com.umurimo.umurimohub.entities.HRActivityLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ReportService
 *
 * Service implementation for generating system reports.
 * Aggregates data from various services/DAOs to provide comprehensive summaries
 * for CEOs and HRs (e.g., total workers, attendance stats, deduction
 * summaries).
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
public class ReportService {
    private WorkerDAO workerDAO = new WorkerDAO();
    private AttendanceDAO attendanceDAO = new AttendanceDAO();
    private HRActivityLogDAO hrActivityLogDAO = new HRActivityLogDAO();
    private UserDAO userDAO = new UserDAO();

    /**
     * Generates a comprehensive report for the CEO dashboard.
     * Includes statistics on workers, HR users, attendance, deductions,
     * punishments, and recent administrative activities.
     *
     * @return a Map containing key metrics and lists for the dashboard
     */
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
            if (count >= 10)
                break;
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

    /**
     * Retrieves a summary list of all workers.
     *
     * @return a list of WorkerDTOs
     */
    public List<WorkerDTO> getWorkerSummary() {
        WorkerService workerService = new WorkerService();
        return workerService.getAllWorkers();
    }

    /**
     * Retrieves a summary list of all attendance records.
     *
     * @return a list of AttendanceDTOs
     */
    public List<AttendanceDTO> getAttendanceSummary() {
        AttendaceService attendanceService = new AttendaceService();
        return attendanceService.getAllAttendance();
    }

    /**
     * Retrieves a summary list of all deductions.
     *
     * @return a list of DeductionDTOs
     */
    public List<DeductionDTO> getDeductionSummary() {
        DeductionService deductionService = new DeductionService();
        return deductionService.getAllDeductions();
    }

    /**
     * Retrieves a detailed summary of HR activities.
     *
     * @return a list of maps containing HR activity details
     */
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
