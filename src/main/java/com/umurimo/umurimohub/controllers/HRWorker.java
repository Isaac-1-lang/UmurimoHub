package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.services.HRActivityLogService;
import com.umurimo.umurimohub.services.WorkerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * HRWorker Servlet
 *
 * Controller for managing worker accounts by HR.
 * Handles listing all workers (GET) and creating new worker profiles (POST).
 * Ensures that worker creation is logged.
 * Only accessible by HR and CEO users.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
@WebServlet(name = "HRWorker", value = "/HRWorker")
public class HRWorker extends HttpServlet {
    private WorkerService workerService = new WorkerService();
    private HRActivityLogService activityLogService = new HRActivityLogService();

    /**
     * Handles HTTP GET requests.
     * Displays the worker management page with a list of all existing workers.
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

        request.setAttribute("workers", workerService.getAllWorkers());
        request.getRequestDispatcher("/html/hr-worker.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     * Processes new worker account creation and sends automated credential emails.
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
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String baseSalaryStr = request.getParameter("baseSalary");

            if (firstName == null || firstName.trim().isEmpty() ||
                    lastName == null || lastName.trim().isEmpty() ||
                    email == null || email.trim().isEmpty()) {
                request.setAttribute("error", "First name, last name, and email are required");
                request.setAttribute("workers", workerService.getAllWorkers());
                request.getRequestDispatcher("/html/hr-worker.jsp").forward(request, response);
                return;
            }

            try {
                Integer baseSalary = baseSalaryStr != null && !baseSalaryStr.trim().isEmpty()
                        ? Integer.parseInt(baseSalaryStr)
                        : 0;

                workerService.createWorker(firstName, lastName, email, phoneNumber, baseSalary);

                // Log HR activity
                if (userId != null) {
                    activityLogService.logActivity(userId,
                            "Created worker account",
                            "Created worker: " + firstName + " " + lastName + " (" + email + ")");
                }

                request.setAttribute("success",
                        "Worker created successfully. An email with credentials has been sent successfully..");
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
            }
        }

        request.setAttribute("workers", workerService.getAllWorkers());
        request.getRequestDispatcher("/html/hr-worker.jsp").forward(request, response);
    }
}
