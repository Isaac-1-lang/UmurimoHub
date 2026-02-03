package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.services.DeductionService;
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
 * @author Isaac-1-lang
 * @version 0.0.1
 */

@WebServlet(name = "Deduction", value = "/Deduction")
public class Deduction extends HttpServlet {
    private DeductionService deductionService = new DeductionService();
    private WorkerService workerService = new WorkerService();
    private HRActivityLogService activityLogService = new HRActivityLogService();

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
        request.setAttribute("deductions", deductionService.getAllDeductions());
        request.getRequestDispatcher("/html/deductions.jsp").forward(request, response);
    }

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
            String amountStr = request.getParameter("amount");
            String reason = request.getParameter("reason");
            String dateStr = request.getParameter("date");

            if (workerId == null || workerId.trim().isEmpty() || 
                amountStr == null || amountStr.trim().isEmpty()) {
                request.setAttribute("error", "Worker and amount are required");
                request.setAttribute("workers", workerService.getActiveWorkers());
                request.setAttribute("deductions", deductionService.getAllDeductions());
                request.getRequestDispatcher("/html/deductions.jsp").forward(request, response);
                return;
            }

            try {
                Integer amount = Integer.parseInt(amountStr);
                Date date = null;
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.parse(dateStr);
                }

                deductionService.createDeduction(workerId, amount, reason, date);
                
                // Log HR activity
                if (userId != null) {
                    activityLogService.logActivity(userId, 
                        "Created salary deduction", 
                        "Created deduction for worker ID: " + workerId + ", Amount: " + amount);
                }
                
                request.setAttribute("success", "Deduction created successfully");
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid amount format");
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
            }
        }

        request.setAttribute("workers", workerService.getActiveWorkers());
        request.setAttribute("deductions", deductionService.getAllDeductions());
        request.getRequestDispatcher("/html/deductions.jsp").forward(request, response);
    }
}
