package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;
import com.umurimo.umurimohub.services.UserService;
import com.umurimo.umurimohub.services.WorkerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    private UserService userService = new UserService();
    private WorkerService workerService = new WorkerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Already logged in, redirect to appropriate dashboard
            String role = (String) session.getAttribute("role");
            if ("CEO".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/html/ceo-dashboard.jsp");
            } else if ("HR".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/html/hr-dashboard.jsp");
            } else if ("WORKER".equals(role)) {
                response.sendRedirect(request.getContextPath() + "/html/worker-dashboard.jsp");
            } else {
                request.getRequestDispatcher("/html/login.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
            return;
        }

        // Try to authenticate as User (CEO/HR) first
        UserEntity user = userService.authenticate(email, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", email);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());
            
            // Check if HR needs to change password
            if ("HR".equals(user.getRole()) && !user.getPasswordChanged()) {
                response.sendRedirect(request.getContextPath() + "/html/change-password.jsp");
                return;
            }
            
            if ("CEO".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/html/ceo-dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/html/hr-dashboard.jsp");
            }
            return;
        }

        // Try to authenticate as Worker
        WorkerEntity worker = workerService.authenticate(email, password);
        if (worker != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", email);
            session.setAttribute("workerId", worker.getWorkerId());
            session.setAttribute("role", "WORKER");
            session.setAttribute("firstName", worker.getFirstName());
            session.setAttribute("lastName", worker.getLastName());
            
            response.sendRedirect(request.getContextPath() + "/html/worker-dashboard.jsp");
            return;
        }

        // Authentication failed
        request.setAttribute("error", "Invalid email or password");
        request.getRequestDispatcher("/html/login.jsp").forward(request, response);
    }
}
