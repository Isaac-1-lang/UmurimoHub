package com.umurimo.umurimohub.controllers;

import com.umurimo.umurimohub.entities.UserEntity;
import com.umurimo.umurimohub.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Handles "Login with Google" using Google Identity Services ID tokens.
 *
 * NOTE: You must replace GOOGLE_CLIENT_ID with your real client ID from Google Cloud Console.
 */
@WebServlet(name = "GoogleLogin", value = "/GoogleLogin")
public class GoogleLogin extends HttpServlet {

    private static final String GOOGLE_CLIENT_ID = "189641498269-hsnqe1t654t1266daflqq25rhqdbgbe7.apps.googleusercontent.com";

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idToken = request.getParameter("credential");
        if (idToken == null || idToken.isEmpty()) {
            request.setAttribute("error", "Google login failed. Please try again.");
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
            return;
        }

        try {
            GoogleUser googleUser = verifyIdToken(idToken);
            if (googleUser == null || googleUser.email == null) {
                request.setAttribute("error", "Could not verify Google account.");
                request.getRequestDispatcher("/html/login.jsp").forward(request, response);
                return;
            }

            // Only allow Google sign-in for existing User accounts (CEO / HR) by email.
            UserEntity user = userService.getUserByEmail(googleUser.email);
            if (user == null) {
                request.setAttribute("error", "No UmurimoHub account is linked to this Google email.");
                request.getRequestDispatcher("/html/login.jsp").forward(request, response);
                return;
            }

            if (!"ACTIVE".equals(user.getStatus())) {
                request.setAttribute("error", "Your account is not active.");
                request.getRequestDispatcher("/html/login.jsp").forward(request, response);
                return;
            }

            // Create session (similar to normal email/password login)
            HttpSession session = request.getSession();
            session.setAttribute("user", user.getEmail());
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());
            session.setAttribute("firstName", user.getFirstName());
            session.setAttribute("lastName", user.getLastName());

            // Redirect based on role
            if ("CEO".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/html/ceo-dashboard.jsp");
            } else if ("HR".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/html/hr-dashboard.jsp");
            } else {
                // Fallback: workers are not expected here but send to worker dashboard if any
                response.sendRedirect(request.getContextPath() + "/html/worker-dashboard.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error during Google login.");
            request.getRequestDispatcher("/html/login.jsp").forward(request, response);
        }
    }

    /**
     * Verifies the ID token with Google's tokeninfo endpoint and extracts email.
     */
    private GoogleUser verifyIdToken(String idToken) throws IOException {
        String urlStr = "https://oauth2.googleapis.com/tokeninfo?id_token=" +
                URLEncoder.encode(idToken, StandardCharsets.UTF_8);
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int status = conn.getResponseCode();
        InputStream is = status == 200 ? conn.getInputStream() : conn.getErrorStream();

        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
        }

        if (status != 200) {
            System.err.println("Failed to verify Google ID token: " + responseBody);
            return null;
        }

        String json = responseBody.toString();

        String aud = extractJsonString(json, "aud");
        if (aud == null || !aud.equals(GOOGLE_CLIENT_ID)) {
            System.err.println("Google ID token 'aud' mismatch.");
            return null;
        }

        GoogleUser user = new GoogleUser();
        user.email = extractJsonString(json, "email");
        user.givenName = extractJsonString(json, "given_name");
        user.familyName = extractJsonString(json, "family_name");
        return user;
    }

    /**
     * Very small helper to extract a string field from a flat JSON object
     * without bringing in a full JSON library.
     */
    private String extractJsonString(String json, String key) {
        String pattern = "\"" + key + "\"";
        int idx = json.indexOf(pattern);
        if (idx == -1) return null;

        int colon = json.indexOf(':', idx);
        if (colon == -1) return null;

        int firstQuote = json.indexOf('"', colon + 1);
        if (firstQuote == -1) return null;

        int secondQuote = json.indexOf('"', firstQuote + 1);
        if (secondQuote == -1) return null;

        return json.substring(firstQuote + 1, secondQuote);
    }

    private static class GoogleUser {
        String email;
        String givenName;
        String familyName;
    }
}

