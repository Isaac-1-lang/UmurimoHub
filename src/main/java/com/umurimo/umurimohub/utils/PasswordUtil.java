package com.umurimo.umurimohub.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * PasswordUtil
 *
 * Utility class for password hashing, verification, and generation.
 * Uses BCrypt for secure password hashing.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
public class PasswordUtil {
    private static final int ROUNDS = 12;

    /**
     * Hashes a plain text password using BCrypt.
     *
     * @param plainPassword the password to hash
     * @return the hashed password string
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(ROUNDS));
    }

    /**
     * Verifies a plain text password against a hashed password.
     *
     * @param plainPassword  the plain text password
     * @param hashedPassword the hashed password to check against
     * @return true if the password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generates a random secure password of the specified length.
     * The password includes uppercase, lowercase, numbers, and special characters.
     *
     * @param length the length of the password to generate
     * @return the generated random password
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}
