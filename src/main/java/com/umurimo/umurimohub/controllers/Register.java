package com.umurimo.umurimohub.controllers;
import com.umurimo.umurimohub.services.UserService;
import com.umurimo.umurimohub.utils.CaptchaValidator;
import com.umurimo.umurimohub.utils.ParamUtil;
import com.umurimo.umurimohub.utils.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Register Servlet
 *
 * Controller for CEO registration.
 * Allows the initial setup of the system by registering the first CEO account.
 * Checks if a CEO already exists to prevent multiple CEO registrations.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {
    private UserService userService = new UserService();

    /**
     * Handles HTTP GET requests.
     * Displays the registration form.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/html/register.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests.
     * Processes CEO registration, including validation and duplicate checks.
     * Auto-logins the CEO upon successful registration.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!CaptchaValidator.validate(request, "captcha")) {
            request.setAttribute("error", "Invalid CAPTCHA. Please try again.");
            request.getRequestDispatcher("/html/register.jsp").forward(request, response);
            return;
        }
        // Receiving the fields to be validated before being stored in the DB
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validation + sanitization (no HTML is trusted; JSP will escape output)
        String firstName;
        String lastName;
        String email;
        try {
            firstName = ParamUtil.requireName(request, "firstName");
            lastName = ParamUtil.requireName(request, "lastName");
            email = ParamUtil.requireEmail(request, "email");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/html/register.jsp").forward(request, response);
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Password is required");
            request.getRequestDispatcher("/html/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/html/register.jsp").forward(request, response);
            return;
        }

        // Generate OTP and send to CEO's email, then redirect to OTP verification step
        String otp = String.format("%06d", (int) (Math.random() * 1_000_000));

        HttpSession session = request.getSession(true);
        session.setAttribute("PENDING_CEO_FIRSTNAME", firstName);
        session.setAttribute("PENDING_CEO_LASTNAME", lastName);
        session.setAttribute("PENDING_CEO_EMAIL", email);
        session.setAttribute("PENDING_CEO_PASSWORD", password);
        session.setAttribute("PENDING_CEO_OTP", otp);

        try {
            EmailUtil.sendCeoOtp(email, otp);
            response.sendRedirect(request.getContextPath() + "/VerifyCEOOTP");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to send OTP email. Please try again.");
            request.getRequestDispatcher("/html/register.jsp").forward(request, response);
        }
    }
}
