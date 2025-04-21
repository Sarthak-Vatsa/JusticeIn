package com.legalease.LegalEaseSB.Repos;

import com.legalease.LegalEaseSB.Models.Requests;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepo extends MongoRepository<Requests, String>
{
    //Requests findByDescription(String description);
    List<Requests> findAllByLid(String lawyer_id);
}
