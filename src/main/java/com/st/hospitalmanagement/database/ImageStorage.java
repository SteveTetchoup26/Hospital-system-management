package com.st.hospitalmanagement.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Emplacement des images de profil, indépendant du système d'exploitation :
 * {@code <dossier utilisateur>/.hospitalmanagement/<sous-dossier>/<fichier>}.
 */
public final class ImageStorage {

    private ImageStorage() {
    }

    public static Path resolve(String subDirectory, String fileName) throws IOException {
        Path directory = Paths.get(System.getProperty("user.home"), ".hospitalmanagement", subDirectory);
        Files.createDirectories(directory);
        return directory.resolve(fileName);
    }
}
