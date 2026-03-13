package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.dtos.ContactDTO;
import com.umurimo.umurimohub.dtos.WorkerDTO;
import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.entities.WorkerEntity;
import com.umurimo.umurimohub.services.MessageService;
import com.umurimo.umurimohub.services.UserService;
import com.umurimo.umurimohub.services.WorkerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChatController", value = "/Chat")
public class ChatController extends HttpServlet {
    private UserService userService = new UserService();
    private WorkerService workerService = new WorkerService();
    private MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        String currentUserId = (String) session.getAttribute("userId");
        if (currentUserId == null) {
            // It might be a worker (workers use workerId in session)
            currentUserId = (String) session.getAttribute("workerId");
        }

        List<ContactDTO> contacts = new ArrayList<>();

        // Add all Users (CEO, HR)
        for (UserEntity u : userService.getAllUsers()) {
            if (!u.getUserId().equals(currentUserId)) {
                contacts.add(new ContactDTO(u.getUserId(), u.getFirstName() + " " + u.getLastName(), u.getRole(), 0L));
            }
        }

        // Add all Workers
        for (WorkerDTO w : workerService.getActiveWorkers()) {
            if (!w.getWorkerId().equals(currentUserId)) {
                contacts.add(new ContactDTO(w.getWorkerId(), w.getFirstName() + " " + w.getLastName(), "WORKER", 0L));
            }
        }

        request.setAttribute("contacts", contacts);
        request.setAttribute("currentUserId", currentUserId);

        request.getRequestDispatcher("/html/chat.jsp").forward(request, response);
    }
}
