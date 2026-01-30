package com.umurimo.umurimohub.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/html/*", "/Register", "/Login", "/HRWorker", 
    "/Attendance", "/Deduction", "/Punishment", "/CEOSummary", "/WorkerProfile", "/CreateHR", "/ChangePassword"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String relativePath = path.substring(contextPath.length());

        // Allow access to login and register pages without authentication
        if (relativePath.equals("/Login") || relativePath.equals("/Register") || 
            relativePath.equals("/") || relativePath.equals("/index.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // Check if user is authenticated
        if (session == null || session.getAttribute("user") == null) {
            httpResponse.sendRedirect(contextPath + "/Login");
            return;
        }

        String userRole = (String) session.getAttribute("role");
        
        // Role-based access control
        if (relativePath.startsWith("/CEOSummary") && !"CEO".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        if (relativePath.startsWith("/CreateHR") && !"CEO".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        if (relativePath.startsWith("/ChangePassword") && !"HR".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        if ((relativePath.startsWith("/HRWorker") || relativePath.startsWith("/Attendance") || 
             relativePath.startsWith("/Deduction") || relativePath.startsWith("/Punishment")) 
            && !"HR".equals(userRole) && !"CEO".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        if (relativePath.startsWith("/WorkerProfile") && !"WORKER".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
