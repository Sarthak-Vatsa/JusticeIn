package com.legalease.LegalEaseSB.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "Lawyers")
public class Lawyers
{
    @Id
    private String id;

    private String email;
    private String name;
    private String phone;
    private String password;
    private String requests;
}
