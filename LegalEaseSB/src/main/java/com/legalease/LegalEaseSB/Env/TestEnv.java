package com.legalease.LegalEaseSB.Env;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestEnv {

    @Value("${MONGO_URI}")
    private String mongoUri;

    @PostConstruct
    public void printEnv() {
        System.out.println("Mongo URI from env: " + mongoUri);
    }
}

