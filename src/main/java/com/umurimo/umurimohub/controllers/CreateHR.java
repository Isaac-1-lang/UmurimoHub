package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CreateHR", value = "/CreateHR")
public class CreateHR extends HttpServlet {
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

        request.setAttribute("hrUsers", userService.getAllHRUsers());
        request.getRequestDispatcher("/html/create-hr.jsp").forward(request, response);
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
        if (!"CEO".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String userId = (String) session.getAttribute("userId");

        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.setAttribute("hrUsers", userService.getAllHRUsers());
            request.getRequestDispatcher("/html/create-hr.jsp").forward(request, response);
            return;
        }

        try {
            userService.createHR(firstName, lastName, email, userId);
            request.setAttribute("success", "HR account created successfully. An email with credentials has been sent (if email service is configured).");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        request.setAttribute("hrUsers", userService.getAllHRUsers());
        request.getRequestDispatcher("/html/create-hr.jsp").forward(request, response);
    }
}
