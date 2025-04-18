package com.legalease.LegalEaseSB.Controllers;

import com.legalease.LegalEaseSB.Models.Consumers;
import com.legalease.LegalEaseSB.Models.Lawyers;
import com.legalease.LegalEaseSB.Models.Requests;
import com.legalease.LegalEaseSB.Repos.ConsumerRepo;
import com.legalease.LegalEaseSB.Repos.RequestRepo;
import com.legalease.LegalEaseSB.Services.ConsumerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/consumer")
public class ConsumerController
{
    @Autowired
    private ConsumerService cservice;

    @Autowired
    private RequestRepo rrepo;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Consumers consumer)
    {
        String status = cservice.register(consumer);
        return ResponseEntity.ok("Saved");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Consumers consumer)
    {
        String status = cservice.login(consumer);
        return ResponseEntity.ok("Login Successfull");
    }

    @PostMapping("/makeRequest")
    public ResponseEntity<String> request(@RequestBody Requests request)
    {
        //c_id will be extracted from the jwt Token

        //l_id will be sent from the frontend itself (the lawyer which the consumer will select)

        String status = cservice.makeRequest(request);
        return ResponseEntity.ok("Request made Successfully");
    }
}
