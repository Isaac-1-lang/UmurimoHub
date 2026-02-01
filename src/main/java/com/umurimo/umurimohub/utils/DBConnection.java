package com.umurimo.umurimohub.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBConnection {
    private static EntityManagerFactory emf;
    private static final String PERSISTENCE_UNIT_NAME = "default";

    static {
        try {
            // Load environment variables
            io.github.cdimascio.dotenv.Dotenv dotenv = io.github.cdimascio.dotenv.Dotenv.configure().ignoreIfMissing()
                    .load();

            java.util.Map<String, String> properties = new java.util.HashMap<>();

            String dbUrl = dotenv.get("DB_URL");
            String dbUser = dotenv.get("DB_USER");
            String dbPassword = dotenv.get("DB_PASSWORD");

            // Use System.getenv as fallback if .env is missing or variables are not there
            if (dbUrl == null)
                dbUrl = System.getenv("DB_URL");
            if (dbUser == null)
                dbUser = System.getenv("DB_USER");
            if (dbPassword == null)
                dbPassword = System.getenv("DB_PASSWORD");

            if (dbUrl != null)
                properties.put("jakarta.persistence.jdbc.url", dbUrl);
            if (dbUser != null)
                properties.put("jakarta.persistence.jdbc.user", dbUser);
            if (dbPassword != null)
                properties.put("jakarta.persistence.jdbc.password", dbPassword);

            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize EntityManagerFactory", e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
