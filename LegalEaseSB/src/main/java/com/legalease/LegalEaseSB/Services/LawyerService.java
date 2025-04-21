package com.legalease.LegalEaseSB.Services;

import com.legalease.LegalEaseSB.Models.Lawyers;
import com.legalease.LegalEaseSB.Models.Requests;
import com.legalease.LegalEaseSB.Repos.LawyerRepo;
import com.legalease.LegalEaseSB.Repos.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LawyerService
{
    @Autowired
    private RequestRepo rrepo;

    @Autowired
    private LawyerRepo lrepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    public String register(Lawyers lawyer)
    {
        Lawyers DBlawyer = lrepo.findByUsername(lawyer.getUsername());
        if(DBlawyer != null){
            return "Consumer already Exists - Try logging in";
        }

        lawyer.setPassword(encoder.encode(lawyer.getPassword()));

        lrepo.save(lawyer);
        return "Successful";
    }

    public String login(Lawyers lawyer)
    {
        Lawyers DBlawyer = lrepo.findByUsername(lawyer.getUsername());

        if(DBlawyer != null && encoder.matches(lawyer.getPassword(), DBlawyer.getPassword())){
            //System.out.println(consumer.getUserType());
            return jwtService.generateToken(lawyer.getUsername(), DBlawyer.getUserType());
        }

        return "Authentication failed";
    }

    public List<Requests> findRequestsWithId()
    {
        //lid can be extracted from the jwt
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Lawyers DBlawyer = lrepo.findByUsername(username);
        String lid = DBlawyer.getId();

        List<Requests> requests = rrepo.findAllByLid(lid);
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
        //jis request ko accept kiya wo hi send kr do backend pr
        Optional<Requests> reqDB = rrepo.findById(request.getId());
        reqDB.get().setStatus("Accepted");

        rrepo.save(reqDB.get());
        return ResponseEntity.ok("Request Accepted");
    }

    public ResponseEntity<String> rejectRequest(@RequestBody Requests request)
    {
        //jis request se accept kiya wo hi send kr do backend pr
        Optional<Requests> reqDB = rrepo.findById(request.getId());
        reqDB.get().setStatus("Rejected");

        rrepo.save(reqDB.get());
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

    public ResponseEntity<String> logout()
    {
        return ResponseEntity.ok("Delete the JWT on the client side");
    }
}
