package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.services.UserService;
import com.umurimo.umurimohub.utils.InputSanitizer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * ChangePassword Servlet
 *
 * Controller for handling password change requests, specifically for HR users.
 * Enforces password updates on first login for HR personnel and allows
 * subsequent updates.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
@WebServlet(name = "ChangePassword", value = "/ChangePassword")
public class ChangePassword extends HttpServlet {
    private UserService userService = new UserService();

    /**
     * Handles HTTP GET requests.
     * Displays the change password form.
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
        if (!"HR".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        request.getRequestDispatcher("/html/change-password.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     * Processes the password change submission, including validation of old/new
     * passwords.
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
        if (!"HR".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Keep passwords as-is (no trimming that could change intent); only basic null/empty checks.
        if (InputSanitizer.trimToNull(oldPassword) == null ||
            InputSanitizer.trimToNull(newPassword) == null ||
            InputSanitizer.trimToNull(confirmPassword) == null) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/html/change-password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match");
            request.getRequestDispatcher("/html/change-password.jsp").forward(request, response);
            return;
        }

        String userId = (String) session.getAttribute("userId");
        if (userService.changePassword(userId, oldPassword, newPassword)) {
            request.setAttribute("success", "Password changed successfully");
            response.sendRedirect(request.getContextPath() + "/html/hr-dashboard.jsp");
        } else {
            request.setAttribute("error", "Invalid old password");
            request.getRequestDispatcher("/html/change-password.jsp").forward(request, response);
        }
    }
}
