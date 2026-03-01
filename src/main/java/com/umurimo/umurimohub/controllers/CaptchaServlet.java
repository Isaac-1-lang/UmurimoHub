package com.umurimo.umurimohub.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.SecureRandom;

@WebServlet(name = "CaptchaServlet", value = "/captcha")
public class CaptchaServlet extends HttpServlet {
    public static final String SESSION_KEY = "CAPTCHA_CODE";
    private static final SecureRandom RNG = new SecureRandom();
    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789abcdefghijklmnopqrst!$^&*_-+@~#"; // no O/0/I/1

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String code = generateCode(6);
        session.setAttribute(SESSION_KEY, code);

        int width = 160;
        int height = 55;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // background
            g.setColor(new Color(245, 245, 245));
            g.fillRect(0, 0, width, height);

            // noise lines
            for (int i = 0; i < 8; i++) {
                g.setColor(new Color(150 + RNG.nextInt(80), 150 + RNG.nextInt(80), 150 + RNG.nextInt(80)));
                int x1 = RNG.nextInt(width);
                int y1 = RNG.nextInt(height);
                int x2 = RNG.nextInt(width);
                int y2 = RNG.nextInt(height);
                g.drawLine(x1, y1, x2, y2);
            }

            // text
            g.setFont(new Font("Arial", Font.BOLD, 28));
            FontMetrics fm = g.getFontMetrics();
            int charWidth = width / (code.length() + 1);
            int baseY = (height + fm.getAscent()) / 2 - 4;

            for (int i = 0; i < code.length(); i++) {
                char c = code.charAt(i);
                g.setColor(new Color(30 + RNG.nextInt(80), 30 + RNG.nextInt(80), 30 + RNG.nextInt(80)));

                double angle = (RNG.nextDouble() - 0.5) * 0.5; // +/- ~0.25 rad
                AffineTransform old = g.getTransform();
                int x = (i + 1) * charWidth - 10;
                g.rotate(angle, x, baseY);
                g.drawString(String.valueOf(c), x, baseY);
                g.setTransform(old);
            }

            // response headers (no cache)
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);

            ImageIO.write(image, "png", response.getOutputStream());
        } finally {
            g.dispose();
        }
    }

    private static String generateCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RNG.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}

