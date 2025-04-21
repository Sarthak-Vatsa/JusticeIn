package com.legalease.LegalEaseSB.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Lawyers")
public class Lawyers
{
    @Id
    private String id;

    private String email;
    private String username;
    private String phone;
    private String password;
    private String requests;
    private int age;

    private String interestedDomains;
    private String expertise;
    private String description;

    private String userType;

    private String authType;

    public Lawyers(String email, String name, String phone, String password, String requests, int age, String interestedDomains, String expertise, String description, String userType, String authType) {
        this.email = email;
        this.username = name;
        this.phone = phone;
        this.password = password;
        this.requests = requests;
        this.age = age;
        this.interestedDomains = interestedDomains;
        this.expertise = expertise;
        this.description = description;
        this.userType = userType;
        this.authType = authType;
    }
}
