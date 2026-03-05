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
 * VerifyCEOOTP Servlet
 *
 * Second step of CEO registration: verifies the OTP sent via email.
 * Only after successful OTP verification is the CEO account actually created.
 */
@WebServlet(name = "VerifyCEOOTP", value = "/VerifyCEOOTP")
public class VerifyCEOOTP extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If there is no pending data in session, redirect to registration
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("PENDING_CEO_EMAIL") == null) {
            response.sendRedirect(request.getContextPath() + "/Register");
            return;
        }
        request.getRequestDispatcher("/html/verify-ceo-otp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("PENDING_CEO_EMAIL") == null) {
            response.sendRedirect(request.getContextPath() + "/Register");
            return;
        }

        String expectedOtp = (String) session.getAttribute("PENDING_CEO_OTP");
        String otpInput = InputSanitizer.sanitizePlainText(request.getParameter("otp"), 10);

        if (expectedOtp == null || otpInput == null || !expectedOtp.equals(otpInput)) {
            request.setAttribute("error", "Invalid OTP. Please check your email and try again.");
            request.getRequestDispatcher("/html/verify-ceo-otp.jsp").forward(request, response);
            return;
        }

        // OTP is correct: create CEO account using data stored in session
        String firstName = (String) session.getAttribute("PENDING_CEO_FIRSTNAME");
        String lastName = (String) session.getAttribute("PENDING_CEO_LASTNAME");
        String email = (String) session.getAttribute("PENDING_CEO_EMAIL");
        String password = (String) session.getAttribute("PENDING_CEO_PASSWORD");

        try {
            userService.registerCEO(firstName, lastName, email, password);

            // Clear pending data
            session.removeAttribute("PENDING_CEO_FIRSTNAME");
            session.removeAttribute("PENDING_CEO_LASTNAME");
            session.removeAttribute("PENDING_CEO_EMAIL");
            session.removeAttribute("PENDING_CEO_PASSWORD");
            session.removeAttribute("PENDING_CEO_OTP");

            // Auto-login CEO
            session.setAttribute("user", email);
            session.setAttribute("role", "CEO");
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);

            response.sendRedirect(request.getContextPath() + "/html/ceo-dashboard.jsp");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/html/verify-ceo-otp.jsp").forward(request, response);
        }
    }
}

