package com.legalease.LegalEaseSB.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Requests")
public class Requests
{
    @Id
    private String id;

    private String cid;
    private String lid;

    private String description;
    private String status;

    private Date date;
}
