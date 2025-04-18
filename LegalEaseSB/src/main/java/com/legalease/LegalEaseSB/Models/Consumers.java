package com.legalease.LegalEaseSB.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "Consumers")
public class Consumers
{
    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private String userType; // "Consumer" or "Lawyer"

    private String authType; // "password" or "google" for handling OAuth

    public Consumers(String name, String email, String password, String userType, String authType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.authType = authType;
    }
}
