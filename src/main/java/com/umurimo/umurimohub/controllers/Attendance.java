package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.services.AttendaceService;
import com.umurimo.umurimohub.services.HRActivityLogService;
import com.umurimo.umurimohub.services.WorkerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Attendance Servlet
 *
 * Controller responsible for managing worker attendance records.
 * Handles both viewing attendance history (GET) and recording new attendance
 * entries (POST).
 * Only accessible by HR and CEO users.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
@WebServlet(name = "Attendance", value = "/Attendance")
public class Attendance extends HttpServlet {
    private AttendaceService attendanceService = new AttendaceService();
    private WorkerService workerService = new WorkerService();
    private HRActivityLogService activityLogService = new HRActivityLogService();

    /**
     * Handles HTTP GET requests.
     * Displays the attendance management page with a list of active workers and
     * attendance records.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"HR".equals(role) && !"CEO".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        request.setAttribute("workers", workerService.getActiveWorkers());
        request.setAttribute("attendances", attendanceService.getAllAttendance());
        request.getRequestDispatcher("/html/attendance.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     * Processes the creation of new attendance records and logs the activity.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"HR".equals(role) && !"CEO".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        String action = request.getParameter("action");
        String userId = (String) session.getAttribute("userId");

        if ("create".equals(action)) {
            String workerId = request.getParameter("workerId");
            String dateStr = request.getParameter("date");
            String status = request.getParameter("status");
            String remarks = request.getParameter("remarks");

            if (workerId == null || workerId.trim().isEmpty() ||
                    status == null || status.trim().isEmpty()) {
                request.setAttribute("error", "Worker and status are required");
                request.setAttribute("workers", workerService.getActiveWorkers());
                request.setAttribute("attendances", attendanceService.getAllAttendance());
                request.getRequestDispatcher("/html/attendance.jsp").forward(request, response);
                return;
            }

            try {
                Date date;
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.parse(dateStr);
                } else {
                    date = new Date();
                }

                attendanceService.createAttendance(workerId, date, status, remarks);

                // Log HR activity
                if (userId != null) {
                    activityLogService.logActivity(userId,
                            "Recorded attendance",
                            "Recorded attendance for worker ID: " + workerId + ", Status: " + status);
                }

                request.setAttribute("success", "Attendance recorded successfully");
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
            }
        }

        request.setAttribute("workers", workerService.getActiveWorkers());
        request.setAttribute("attendances", attendanceService.getAllAttendance());
        request.getRequestDispatcher("/html/attendance.jsp").forward(request, response);
    }
}
