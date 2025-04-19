package com.legalease.LegalEaseSB.Services;

import com.legalease.LegalEaseSB.Models.Lawyers;
import com.legalease.LegalEaseSB.Models.Requests;
import com.legalease.LegalEaseSB.Repos.LawyerRepo;
import com.legalease.LegalEaseSB.Repos.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LawyerService
{
    @Autowired
    private RequestRepo rrepo;

    @Autowired
    private LawyerRepo lrepo;

    public String register(Lawyers lawyer)
    {
        lrepo.save(lawyer);
        return "Successful";
    }

    public String login(Lawyers lawyer)
    {
        return "Successful";
    }

    public List<Requests> findRequestsWithId(@RequestBody Lawyers lawyer)
    {
        //lid can be extracted from the jwt as well
        String lid = lawyer.getId();
        List<Requests> requests = rrepo.findAllById(Collections.singleton(lid));
        List<Requests> requiredRequests = new ArrayList<>();
        for(Requests req: requests)
        {
            if(req.getStatus().equalsIgnoreCase("Pending")){
                requiredRequests.add(req);
            }
        }

        return requiredRequests;
    }

    public ResponseEntity<String> acceptRequest(@RequestBody Requests request)
    {
        //jis request se accept kiya wo hi send kr do backend pr
        Optional<Requests> reqDB = rrepo.findById(request.getId());
        reqDB.get().setStatus("Accepted");
        return ResponseEntity.ok("Request Accepted");
    }

    public ResponseEntity<String> rejectRequest(@RequestBody Requests request)
    {
        //jis request se accept kiya wo hi send kr do backend pr
        Optional<Requests> reqDB = rrepo.findById(request.getId());
        reqDB.get().setStatus("Rejected");
        return ResponseEntity.ok("Request Declined");
    }

    public List<Lawyers> findLawyers(@RequestParam(required = false) String search)
    {
        List<Lawyers> lawyers;
        if(search != null && search.trim().length() != 0)
        {
            String regex = ".*" + search.trim() + ".*";
            lawyers = lrepo.findByDescriptionRegexIgnoreCase(regex);
        }
        else{
            lawyers = lrepo.findAll();
        }

        return lawyers;
    }

}
