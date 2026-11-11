package com.st.hospitalmanagement.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connexion MySQL.
 *
 * Les identifiants ne sont pas dans le code : ils sont lus dans le fichier
 * {@code <dossier utilisateur>/.hospitalmanagement/db.properties} (clés url, user, password).
 * Voir db.properties.example à la racine du projet.
 */
public class Database {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/hospital?continueBatchOnError=false";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private static final Path CONFIG_FILE =
            Paths.get(System.getProperty("user.home"), ".hospitalmanagement", "db.properties");

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Driver MySQL non trouvé");
        }

        Properties config = new Properties();
        if (Files.isRegularFile(CONFIG_FILE)) {
            try (InputStream in = Files.newInputStream(CONFIG_FILE)) {
                config.load(in);
            } catch (IOException e) {
                System.err.println("Impossible de lire " + CONFIG_FILE + " : " + e.getMessage());
            }
        } else {
            System.err.println("Fichier de configuration absent : " + CONFIG_FILE
                    + " (valeurs par défaut utilisées)");
        }

        URL = config.getProperty("url", DEFAULT_URL).trim();
        USER = config.getProperty("user", DEFAULT_USER).trim();
        PASSWORD = config.getProperty("password", DEFAULT_PASSWORD);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
