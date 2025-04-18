package com.legalease.LegalEaseSB.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "Requests")
public class Requests
{
    @Id
    private String id;

    private String c_id;
    private String l_id;

    private String description;
    private String status;

    private Date date;
}
