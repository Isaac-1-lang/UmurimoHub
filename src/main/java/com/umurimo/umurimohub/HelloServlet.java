package com.umurimo.umurimohub;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * HelloServlet
 *
 * Simple servlet for testing server connectivity and response.
 * Displays a "Hello World" message HTML page.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2024
 */
@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    /**
     * Initializes the servlet and sets the initial message.
     */
    public void init() {
        message = "Hello World!";
    }

    /**
     * Handles HTTP GET requests.
     * Writes a simple HTML response containing the welcome message.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    /**
     * Called by the web container to indicate to a servlet that the servlet is
     * being taken out of service.
     */
    public void destroy() {
    }
}