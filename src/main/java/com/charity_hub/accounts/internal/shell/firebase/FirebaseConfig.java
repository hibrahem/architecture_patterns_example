package com.charity_hub.accounts.internal.shell.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Configuration
public class FirebaseConfig {
    
    private final String serviceAccountPath;

    public FirebaseConfig(@Value("${firebase.service-account-path}") String serviceAccountPath) {
        this.serviceAccountPath = serviceAccountPath;
    }

    @Bean
    public FirebaseAuth initFirebaseAuth() throws IOException {
        GoogleCredentials credentials;
        try (FileInputStream serviceAccount = new FileInputStream(ResourceUtils.getFile(serviceAccountPath))) {
            credentials = GoogleCredentials.fromStream(serviceAccount);
        }

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .build();

        FirebaseApp appInstance = null;

        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        for (FirebaseApp app : firebaseApps) {
            if (FirebaseApp.DEFAULT_APP_NAME.equals(app.getName())) {
                appInstance = app;
                break;
            }
        }

        return FirebaseAuth.getInstance(
            appInstance != null ? appInstance : FirebaseApp.initializeApp(options)
        );
    }
}