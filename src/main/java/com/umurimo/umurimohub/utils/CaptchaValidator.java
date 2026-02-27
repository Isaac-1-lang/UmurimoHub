package com.umurimo.umurimohub.utils;

import com.umurimo.umurimohub.controllers.CaptchaServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class CaptchaValidator {
    private CaptchaValidator() {}

    public static boolean validate(HttpServletRequest request, String userInputParamName) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        Object expectedObj = session.getAttribute(CaptchaServlet.SESSION_KEY);
        // one-time use to prevent replay
        session.removeAttribute(CaptchaServlet.SESSION_KEY);

        if (!(expectedObj instanceof String expected)) return false;

        String got = InputSanitizer.sanitizePlainText(request.getParameter(userInputParamName), 20);
        if (got == null) return false;

        return expected.equalsIgnoreCase(got);
    }
}

