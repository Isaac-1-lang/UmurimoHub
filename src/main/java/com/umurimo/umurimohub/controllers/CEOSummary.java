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

@WebServlet(name = "CEOSummary", value = "/CEOSummary")
public class CEOSummary extends HttpServlet {
    private ReportService reportService = new ReportService();
    private UserService userService = new UserService();

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
