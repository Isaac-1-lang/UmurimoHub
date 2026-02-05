package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.services.ReportService;
import com.umurimo.umurimohub.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * CEOSummary Servlet
 *
 * Controller for the CEO Dashboard.
 * Retrieves comprehensive reports and summaries (workers, attendance,
 * financials, HR activity)
 * using the ReportService and displays them on the dashboard.
 * Only accessible by CEO users.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
@WebServlet(name = "CEOSummary", value = "/CEOSummary")
public class CEOSummary extends HttpServlet {
    private ReportService reportService = new ReportService();
    private UserService userService = new UserService();

    /**
     * Handles HTTP GET requests.
     * Aggregates data for the CEO dashboard display.
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
        if (!"CEO".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        // Get summary report
        request.setAttribute("report", reportService.getCEOReport());
        request.setAttribute("workers", reportService.getWorkerSummary());
        request.setAttribute("attendances", reportService.getAttendanceSummary());
        request.setAttribute("deductions", reportService.getDeductionSummary());
        request.setAttribute("hrActivities", reportService.getHRActivitySummary());
        request.setAttribute("hrUsers", userService.getAllHRUsers());

        request.getRequestDispatcher("/html/ceo-dashboard.jsp").forward(request, response);
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
