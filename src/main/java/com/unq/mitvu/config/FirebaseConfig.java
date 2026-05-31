package com.unq.mitvu.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void inicializarFirebase() {
        try {
            InputStream serviceAccount;

            String firebaseEnvCredentials = System.getenv("FIREBASE_CREDENTIALS");

            if (firebaseEnvCredentials != null && !firebaseEnvCredentials.trim().isEmpty()) {
                serviceAccount = new ByteArrayInputStream(firebaseEnvCredentials.getBytes(StandardCharsets.UTF_8));
            } else {
                serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");
            }

            if (serviceAccount == null) {
                throw new RuntimeException("ERROR FATAL: No se encontraron credenciales de Firebase en variables de entorno ni en el archivo físico.");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("✅ Firebase inicializado correctamente.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error crítico al iniciar la conexión con Firebase: " + e.getMessage(), e);
        }
    }
}