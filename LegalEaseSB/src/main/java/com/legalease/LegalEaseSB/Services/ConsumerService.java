package com.legalease.LegalEaseSB.Services;

import com.legalease.LegalEaseSB.Models.Consumers;
import com.legalease.LegalEaseSB.Models.Lawyers;
import com.legalease.LegalEaseSB.Models.Requests;
import com.legalease.LegalEaseSB.Repos.ConsumerRepo;
import com.legalease.LegalEaseSB.Repos.LawyerRepo;
import com.legalease.LegalEaseSB.Repos.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ConsumerService
{
    @Autowired
    private ConsumerRepo crepo;

    @Autowired
    private LawyerRepo lrepo;

    @Autowired
    private RequestRepo rrepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;


    public String register(Consumers consumer)
    {
        Consumers cons = crepo.findByUsername(consumer.getUsername());
        if(cons != null){
            return "Consumer already Exists - Try logging in";
        }

        consumer.setPassword(encoder.encode(consumer.getPassword()));
        crepo.save(consumer);

        return "Successful";
    }

    public String login(Consumers consumer) {
        Consumers obj = crepo.findByUsername(consumer.getUsername());

        if(obj != null && encoder.matches(consumer.getPassword(), obj.getPassword())){
            System.out.println(consumer.getUserType());
            return jwtService.generateToken(consumer.getUsername(), obj.getUserType());
        }

        return "Authentication failed";
    }


    public String makeRequest(Requests request)
    {
        //c_id will be extracted from the jwt Token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // JWT subject = username

        Consumers consumer = crepo.findByUsername(username);
        request.setCid(consumer.getId());

        //l_id will be sent from the frontend itself (the lawyer which the consumer will select)

        request.setDate(new Date());
        request.setStatus("Pending");
        rrepo.save(request);
        return "Successful";
    }

    public ResponseEntity<String> logout()
    {
        return ResponseEntity.ok("Delete the token on the client side");
    }

    public String registerOAuthConsumer(Consumers consumer)
    {
        if (crepo.findByUsername(consumer.getEmail()) != null) {
            return "Consumer already exists.";
        }
        crepo.save(consumer);
        String token = jwtService.generateToken(consumer.getEmail(), "Consumer");

        return token;
    }
}
