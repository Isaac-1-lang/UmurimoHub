package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;
import com.umurimo.umurimohub.services.UserService;
import com.umurimo.umurimohub.services.WorkerService;
import com.umurimo.umurimohub.utils.CaptchaValidator;
import com.umurimo.umurimohub.utils.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Login Servlet
 *
 * Controller for user authentication.
 * Handles login requests for all user types (CEO, HR, Worker) and routes them
 * to their respective dashboards upon successful authentication.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    private UserService userService = new UserService();
    private WorkerService workerService = new WorkerService();

    /**
     * Handles HTTP GET requests.
     * Displays the login page or redirects logged-in users to their dashboard.
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

    /**
     * Handles HTTP POST requests.
     * Processes login credentials and initiates user sessions.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simple throttling (per session) to reduce brute force
        HttpSession throttleSession = request.getSession(true);
        Long lockUntil = (Long) throttleSession.getAttribute("LOGIN_LOCK_UNTIL");
        if (lockUntil != null && lockUntil > System.currentTimeMillis()) {
            request.setAttribute("error", "Too many failed attempts. Try again later.");
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
            return;
        }

        // CAPTCHA check
        if (!CaptchaValidator.validate(request, "captcha")) {
            request.setAttribute("error", "Invalid CAPTCHA. Please try again.");
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
            return;
        }

        String password = request.getParameter("password");
        String email;
        try {
            email = ParamUtil.requireEmail(request, "email");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Password is required");
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
            return;
        }

        // Try to authenticate as User (CEO/HR) first
        UserEntity user = userService.authenticate(email, password);
        if (user != null) {
            // reset throttle counters on success
            throttleSession.removeAttribute("LOGIN_FAIL_COUNT");
            throttleSession.removeAttribute("LOGIN_LOCK_UNTIL");

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
            // reset throttle counters on success
            throttleSession.removeAttribute("LOGIN_FAIL_COUNT");
            throttleSession.removeAttribute("LOGIN_LOCK_UNTIL");

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
        Integer failCount = (Integer) throttleSession.getAttribute("LOGIN_FAIL_COUNT");
        failCount = (failCount == null) ? 1 : (failCount + 1);
        throttleSession.setAttribute("LOGIN_FAIL_COUNT", failCount);
        if (failCount >= 5) {
            throttleSession.setAttribute("LOGIN_LOCK_UNTIL", System.currentTimeMillis() + (5L * 60L * 1000L));
        }

        request.setAttribute("error", "Invalid email or password");
        request.getRequestDispatcher("/html/login.jsp").forward(request, response);
    }
}
