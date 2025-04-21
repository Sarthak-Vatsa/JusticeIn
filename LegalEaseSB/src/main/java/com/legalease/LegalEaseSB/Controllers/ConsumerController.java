package com.legalease.LegalEaseSB.Controllers;

import com.legalease.LegalEaseSB.Models.Consumers;
import com.legalease.LegalEaseSB.Models.Requests;
import com.legalease.LegalEaseSB.Repos.RequestRepo;
import com.legalease.LegalEaseSB.Services.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<Map<String, String>> login(@RequestBody Consumers consumer)
    {
        String jwt = cservice.login(consumer);
        return ResponseEntity.ok(Map.of("token", jwt));
    }

    @PostMapping("/makeRequest")
    public ResponseEntity<String> request(@RequestBody Requests request)
    {
        String status = cservice.makeRequest(request);
        return ResponseEntity.ok("Request made Successfully");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout()
    {
        return cservice.logout();
    }
}
