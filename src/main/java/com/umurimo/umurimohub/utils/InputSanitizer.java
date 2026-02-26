package com.umurimo.umurimohub.utils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.regex.Pattern;

public final class InputSanitizer {
    private InputSanitizer() {}

    // Simple allow-list patterns (tune as needed)
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[\\p{L} .'-]{1,100}$"); // letters + basic punctuation
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[0-9+()\\-\\s]{5,30}$");

    public static String trimToNull(String value) {
        if (value == null) return null;
        String v = value.trim();
        return v.isEmpty() ? null : v;
    }

    public static String stripControls(String value) {
        if (value == null) return null;
        StringBuilder sb = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            // Keep common whitespace; drop other control chars
            if (c == '\n' || c == '\r' || c == '\t' || !Character.isISOControl(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String limit(String value, int maxLen) {
        if (value == null) return null;
        if (maxLen <= 0) return "";
        return value.length() <= maxLen ? value : value.substring(0, maxLen);
    }

    public static String sanitizePlainText(String value, int maxLen) {
        String v = stripControls(trimToNull(value));
        v = limit(v, maxLen);
        return v;
    }

    public static String sanitizeEmail(String email) {
        String v = sanitizePlainText(email, 254);
        if (v == null) return null;
        v = v.toLowerCase(Locale.ROOT);
        return EMAIL_PATTERN.matcher(v).matches() ? v : null;
    }

    public static String sanitizeName(String name) {
        String v = sanitizePlainText(name, 100);
        if (v == null) return null;
        return NAME_PATTERN.matcher(v).matches() ? v : null;
    }

    public static String sanitizePhone(String phone) {
        String v = sanitizePlainText(phone, 30);
        if (v == null) return null;
        return PHONE_PATTERN.matcher(v).matches() ? v : null;
    }

    public static Integer parseInt(String value, Integer min, Integer max) {
        String v = sanitizePlainText(value, 20);
        if (v == null) return null;
        try {
            int parsed = Integer.parseInt(v);
            if (min != null && parsed < min) return null;
            if (max != null && parsed > max) return null;
            return parsed;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Very small normalization to avoid weird Unicode tricks in identifiers.
     * Not a full security solution, but helps.
     */
    public static String toSafeAscii(String value, int maxLen) {
        String v = sanitizePlainText(value, maxLen);
        if (v == null) return null;
        byte[] bytes = v.getBytes(StandardCharsets.US_ASCII);
        return new String(bytes, StandardCharsets.US_ASCII);
    }
}

