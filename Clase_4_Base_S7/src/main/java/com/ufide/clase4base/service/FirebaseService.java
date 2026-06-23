package com.ufide.clase4base.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import jakarta.annotation.PostConstruct;

/**
 * Servicio para subir archivos a Firebase Storage.
 *
 * BONUS de la Clase 5. Si firebase-key.json no existe en resources/,
 * el servicio queda desactivado pero la app sigue arrancando.
 */
@Service
public class FirebaseService {

    @Value("${firebase.bucket:}")
    private String bucketName;

    private boolean activo = false;

    @PostConstruct
    public void init() {
        if (bucketName == null || bucketName.isBlank()) {
            System.out.println("[Firebase] bucket no configurado. Servicio desactivado.");
            return;
        }
        try (InputStream serviceAccount =
                 getClass().getResourceAsStream("/firebase-key.json")) {
            if (serviceAccount == null) {
                System.out.println("[Firebase] firebase-key.json no encontrado. Servicio desactivado.");
                return;
            }
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(bucketName)
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
            activo = true;
            System.out.println("[Firebase] inicializado con bucket " + bucketName);
        } catch (IOException e) {
            System.out.println("[Firebase] error inicializando: " + e.getMessage());
        }
    }

    public boolean isActivo() {
        return activo;
    }

    public String subir(MultipartFile file) throws IOException {
        if (!activo) {
            throw new IllegalStateException("Firebase no esta activo. Verifica firebase-key.json y firebase.bucket en application.properties.");
        }
        String nombre = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.create(nombre, file.getBytes(), file.getContentType());
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        return String.format("https://storage.googleapis.com/%s/%s",
                bucket.getName(), nombre);
    }
}
