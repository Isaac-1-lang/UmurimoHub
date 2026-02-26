package com.umurimo.umurimohub.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public final class ParamUtil {
    private ParamUtil() {}

    public static String getText(HttpServletRequest req, String name, int maxLen) {
        return InputSanitizer.sanitizePlainText(req.getParameter(name), maxLen);
    }

    public static String requireText(HttpServletRequest req, String name, int maxLen) {
        String v = getText(req, name, maxLen);
        if (v == null) throw new IllegalArgumentException(name + " is required");
        return v;
    }

    public static String getEmail(HttpServletRequest req, String name) {
        return InputSanitizer.sanitizeEmail(req.getParameter(name));
    }

    public static String requireEmail(HttpServletRequest req, String name) {
        String v = getEmail(req, name);
        if (v == null) throw new IllegalArgumentException("Valid " + name + " is required");
        return v;
    }

    public static String requireName(HttpServletRequest req, String name) {
        String v = InputSanitizer.sanitizeName(req.getParameter(name));
        if (v == null) throw new IllegalArgumentException("Valid " + name + " is required");
        return v;
    }

    public static String getPhone(HttpServletRequest req, String name) {
        return InputSanitizer.sanitizePhone(req.getParameter(name));
    }

    public static Integer requireInt(HttpServletRequest req, String name, Integer min, Integer max) {
        Integer v = InputSanitizer.parseInt(req.getParameter(name), min, max);
        if (v == null) throw new IllegalArgumentException("Valid " + name + " is required");
        return v;
    }

    public static String requireOneOf(HttpServletRequest req, String name, Set<String> allowed) {
        String v = getText(req, name, 50);
        if (v == null || !allowed.contains(v)) {
            throw new IllegalArgumentException("Invalid " + name);
        }
        return v;
    }
}

