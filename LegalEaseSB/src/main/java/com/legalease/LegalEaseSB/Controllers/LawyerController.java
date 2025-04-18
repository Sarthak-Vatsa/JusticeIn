package com.legalease.LegalEaseSB.Controllers;

import com.legalease.LegalEaseSB.Models.Consumers;
import com.legalease.LegalEaseSB.Models.Lawyers;
import com.legalease.LegalEaseSB.Models.Requests;
import com.legalease.LegalEaseSB.Repos.LawyerRepo;
import com.legalease.LegalEaseSB.Repos.RequestRepo;
import com.legalease.LegalEaseSB.Services.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/lawyer")
public class LawyerController
{
    @Autowired
    private RequestRepo rrepo;

    @Autowired
    private LawyerRepo lrepo;

    @Autowired
    private LawyerService lservice;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Lawyers lawyer)
    {
        String status = lservice.register(lawyer);
        return ResponseEntity.ok("Saved");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Lawyers lawyer)
    {
        String status = lservice.login(lawyer);
        return ResponseEntity.ok("Login Successfull");
    }


    @GetMapping("/getRequests")
    public List<Requests> findRequestsWithId(@RequestBody Lawyers lawyer)
    {
        List<Requests> requests = lservice.findRequestsWithId(lawyer);
        return requests;
    }

    @PostMapping("/acceptRequest")
    public ResponseEntity<String> acceptRequest(@RequestBody Requests request)
    {
        //jis request se accept kiya wo hi send kr do backend pr
        lservice.acceptRequest(request);
        return ResponseEntity.ok("Request Accepted");
    }

    @PostMapping("rejectRequest")
    public ResponseEntity<String> rejectRequest(@RequestBody Requests request)
    {
        //jis request se accept kiya wo hi send kr do backend pr
        lservice.rejectRequest(request);
        return ResponseEntity.ok("Request Declined");
    }

    @GetMapping("/getlawyers")
    public List<Lawyers> findLawyers(@RequestParam(required = false) String search)
    {
        List<Lawyers> lawyers = lservice.findLawyers(search);
        return lawyers;
    }
}
