package com.legalease.LegalEaseSB.Env;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestEnv {

    @Value("${MONGO_URI}")
    private String mongoUri;

    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String googleClientSecret;

    @PostConstruct
    public void printEnv() {
        System.out.println("GOOGLE_CLIENT_ID: " + googleClientId);
        System.out.println("GOOGLE_CLIENT_SECRET: " + googleClientSecret);
        System.out.println("Mongo URI from env: " + mongoUri);
    }
}

