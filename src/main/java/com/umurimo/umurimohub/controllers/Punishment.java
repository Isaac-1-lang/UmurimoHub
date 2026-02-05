package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.services.HRActivityLogService;
import com.umurimo.umurimohub.services.PunishmentService;
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
 * Punishment Servlet
 *
 * Controller for managing disciplinary actions (punishments).
 * Handles the display of punishment records (GET) and the creation of new
 * punishments (POST).
 * Logs all disciplinary actions for audit purposes.
 * Only accessible by HR and CEO users.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
@WebServlet(name = "Punishment", value = "/Punishment")
public class Punishment extends HttpServlet {
    private PunishmentService punishmentService = new PunishmentService();
    private WorkerService workerService = new WorkerService();
    private HRActivityLogService activityLogService = new HRActivityLogService();

    /**
     * Handles HTTP GET requests.
     * Displays the punishments management page with a list of active workers and
     * punishment records.
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
        request.setAttribute("punishments", punishmentService.getAllPunishments());
        request.getRequestDispatcher("/html/punishments.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     * Processes new punishment records, including validation and logging.
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
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String dateStr = request.getParameter("date");

            if (workerId == null || workerId.trim().isEmpty() ||
                    title == null || title.trim().isEmpty()) {
                request.setAttribute("error", "Worker and title are required");
                request.setAttribute("workers", workerService.getActiveWorkers());
                request.setAttribute("punishments", punishmentService.getAllPunishments());
                request.getRequestDispatcher("/html/punishments.jsp").forward(request, response);
                return;
            }

            try {
                Date date = null;
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.parse(dateStr);
                }

                punishmentService.createPunishment(workerId, title, description, date);

                // Log HR activity
                if (userId != null) {
                    activityLogService.logActivity(userId,
                            "Created disciplinary action",
                            "Created punishment for worker ID: " + workerId + ", Title: " + title);
                }

                request.setAttribute("success", "Disciplinary action recorded successfully");
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
            }
        }

        request.setAttribute("workers", workerService.getActiveWorkers());
        request.setAttribute("punishments", punishmentService.getAllPunishments());
        request.getRequestDispatcher("/html/punishments.jsp").forward(request, response);
    }
}
