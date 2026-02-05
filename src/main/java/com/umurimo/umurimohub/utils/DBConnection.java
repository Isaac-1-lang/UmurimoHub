package com.umurimo.umurimohub.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * DBConnection
 *
 * Utility class to handle database connections using JPA EntityManagerFactory.
 * This class manages the lifecycle of the EntityManagerFactory and provides
 * EntityManagers.
 * It reads configuration from environment variables or .env file with fallback
 * defaults.
 *
 * @author Isaac-1-lang
 * @version 1.0
 * @since 2026
 */
public class DBConnection {
    private static EntityManagerFactory emf;
    private static final String PERSISTENCE_UNIT_NAME = "default";
    /**
     * The EntityManagerFactory instance.
     * 
     * @throws RuntimeException if EntityManagerFactory cannot be created
     */

    static {
        initialize(null);
    }

    public static void initialize(java.util.Map<String, String> overrides) {
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

            // Hard fallback for local development if environment variables are missing
            if (dbUrl == null)
                dbUrl = "jdbc:postgresql://localhost:5432/umurimohub";
            if (dbUser == null)
                dbUser = "postgres";
            if (dbPassword == null)
                dbPassword = "121402pr0732021";

            if (dbUrl != null)
                properties.put("jakarta.persistence.jdbc.url", dbUrl);
            if (dbUser != null)
                properties.put("jakarta.persistence.jdbc.user", dbUser);
            if (dbPassword != null)
                properties.put("jakarta.persistence.jdbc.password", dbPassword);

            // Apply overrides if any (useful for testing)
            if (overrides != null) {
                properties.putAll(overrides);
            }

            // Close existing EMF if open to avoid leaks when re-initializing
            closeEntityManagerFactory();

            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize EntityManagerFactory", e);
        }
    }

    /**
     * Creates and returns a new EntityManager.
     * The caller is responsible for closing the EntityManager.
     *
     * @return a new EntityManager instance
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Closes the EntityManagerFactory.
     * Should be called when the application is shutting down.
     */
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
