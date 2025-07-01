package com.project.attable.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${googleServicesJSONFile}")
    String googleServicesJSONFile;

    @Value("${firebaseDatabaseUrl}")
    String firebaseDatabaseUrl;

    @PostConstruct
    public void initializeApp() throws IOException {
        InputStream serviceAccount = FirebaseConfig.class.getClassLoader().getResourceAsStream(googleServicesJSONFile);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(firebaseDatabaseUrl)
                .build();
        FirebaseApp.initializeApp(options);
    }


}
