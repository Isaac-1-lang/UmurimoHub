package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.daos.UserDAO;
import com.umurimo.umurimohub.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;



/**
 * @author Isaac-1-lang
 * @version 0.0.1
 */


@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {
    private UserService userService = new UserService();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if CEO already exists
        if (userDAO.findByRole("CEO").size() > 0) {
            request.setAttribute("error", "CEO account already exists. Please login instead.");
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/html/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validation
        if (firstName == null || firstName.trim().isEmpty() ||
            lastName == null || lastName.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/html/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/html/register.jsp").forward(request, response);
            return;
        }

        try {
            // Check if CEO already exists
            if (userDAO.findByRole("CEO").size() > 0) {
                request.setAttribute("error", "CEO account already exists. Please login instead.");
                request.getRequestDispatcher("/html/login.jsp").forward(request, response);
                return;
            }

            userService.registerCEO(firstName, lastName, email, password);
            
            // Auto-login after registration
            HttpSession session = request.getSession();
            session.setAttribute("user", email);
            session.setAttribute("role", "CEO");
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);
            
            response.sendRedirect(request.getContextPath() + "/html/ceo-dashboard.jsp");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/html/register.jsp").forward(request, response);
        }
    }
}
