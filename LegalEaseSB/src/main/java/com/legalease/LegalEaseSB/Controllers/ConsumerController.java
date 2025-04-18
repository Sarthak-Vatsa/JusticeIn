package com.legalease.LegalEaseSB.Controllers;

import com.legalease.LegalEaseSB.Models.Consumers;
import com.legalease.LegalEaseSB.Repos.ConsumerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController
{
    @Autowired
    private ConsumerRepo crepo;

    @PostMapping("/save")
    public ResponseEntity<String> save()
    {
        //mongo will auto-generate an id
        Consumers temp = new Consumers(
                "ABC", "AGJCD", "123", "CONSUMER", "Password"
        );
        crepo.save(temp);
        return ResponseEntity.ok("Saved");
    }
}
