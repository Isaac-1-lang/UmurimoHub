package com.umurimo.umurimohub.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "SecurityHeadersFilter", urlPatterns = "/*")
public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse http) {
            // Basic hardening headers
            http.setHeader("X-Content-Type-Options", "nosniff");
            http.setHeader("X-Frame-Options", "DENY");
            http.setHeader("Referrer-Policy", "no-referrer");
            http.setHeader("Permissions-Policy", "geolocation=(), microphone=(), camera=()");
            http.setHeader("Content-Security-Policy",
                    "default-src 'self'; " +
                            "img-src 'self' data:; " +
                            "style-src 'self' https://fonts.googleapis.com 'unsafe-inline'; " +
                            "font-src 'self' https://fonts.gstatic.com; " +
                            "script-src 'self' https://accounts.google.com https://apis.google.com 'unsafe-inline'; " +
                            "frame-src https://accounts.google.com; " +
                            "base-uri 'self'; " +
                            "frame-ancestors 'none';"
            );
        }

        chain.doFilter(request, response);
    }
}

