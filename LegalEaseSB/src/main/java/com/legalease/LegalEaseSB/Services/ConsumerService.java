package com.legalease.LegalEaseSB.Services;

import com.legalease.LegalEaseSB.Models.Consumers;
import com.legalease.LegalEaseSB.Models.Lawyers;
import com.legalease.LegalEaseSB.Models.Requests;
import com.legalease.LegalEaseSB.Repos.ConsumerRepo;
import com.legalease.LegalEaseSB.Repos.LawyerRepo;
import com.legalease.LegalEaseSB.Repos.RequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConsumerService
{
    @Autowired
    private ConsumerRepo crepo;

    @Autowired
    private LawyerRepo lrepo;

    @Autowired
    private RequestRepo rrepo;

    public String register(Consumers consumer)
    {
        crepo.save(consumer);
        return "Successful";
    }

    public String login(Consumers consumer)
    {
        return "Successful";
    }

    public String makeRequest(Requests request)
    {
        //c_id will be extracted from the jwt Token

        //l_id will be sent from the frontend itself (the lawyer which the consumer will select)

        request.setDate(new Date());
        request.setStatus("Pending");
        rrepo.save(request);
        return "Successful";
    }
}
