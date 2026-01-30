package com.umurimo.umurimohub.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "izerejoshua@gmail.com"; 
    private static final String SMTP_PASSWORD = "lfkb sgdn lsjq ttnr"; 

    public static boolean sendEmail(String to, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendHRCredentials(String email, String firstName, String password) {
        String subject = "Welcome to UmurimoHub - Your HR Account Credentials";
        String body = "Dear " + firstName + ",\n\n" +
                "Your HR account has been created successfully.\n\n" +
                "Login Credentials:\n" +
                "Email: " + email + "\n" +
                "Password: " + password + "\n\n" +
                "Please change your password after your first login.\n\n" +
                "Best regards,\n" +
                "UmurimoHub Team";
        sendEmail(email, subject, body);
    }

    public static void sendWorkerCredentials(String email, String firstName, String password) {
        String subject = "Welcome to UmurimoHub - Your Worker Account Credentials";
        String body = "Dear " + firstName + ",\n\n" +
                "Your worker account has been created successfully.\n\n" +
                "Login Credentials:\n" +
                "Email: " + email + "\n" +
                "Password: " + password + "\n\n" +
                "You can now log in to view your attendance, deductions, and disciplinary records.\n\n" +
                "Best regards,\n" +
                "UmurimoHub Team";
        sendEmail(email, subject, body);
    }
}
