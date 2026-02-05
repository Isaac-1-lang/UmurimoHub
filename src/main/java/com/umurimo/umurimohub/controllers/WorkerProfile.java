package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.services.AttendaceService;
import com.umurimo.umurimohub.services.DeductionService;
import com.umurimo.umurimohub.services.PunishmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * WorkerProfile Servlet
 *
 * Controller for the Worker Dashboard.
 * Retrieves and displays personal data for the logged-in worker, including
 * attendance records, deductions, punishments, and stats.
 * Only accessible by Worker users.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
@WebServlet(name = "WorkerProfile", value = "/WorkerProfile")
public class WorkerProfile extends HttpServlet {
    private AttendaceService attendanceService = new AttendaceService();
    private DeductionService deductionService = new DeductionService();
    private PunishmentService punishmentService = new PunishmentService();

    /**
     * Handles HTTP GET requests.
     * Aggregates personal data for the worker dashboard.
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
        if (!"WORKER".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        String workerId = (String) session.getAttribute("workerId");
        if (workerId == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        // Get worker's own data
        request.setAttribute("attendances", attendanceService.getAttendanceByWorker(workerId));
        request.setAttribute("deductions", deductionService.getDeductionsByWorker(workerId));
        request.setAttribute("punishments", punishmentService.getPunishmentsByWorker(workerId));
        request.setAttribute("totalDeductions", deductionService.getTotalDeductionsByWorker(workerId));

        request.getRequestDispatcher("/html/worker-dashboard.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     * Redirects to the GET handler.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
