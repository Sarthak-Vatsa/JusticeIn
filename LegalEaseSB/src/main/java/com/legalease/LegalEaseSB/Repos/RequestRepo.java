package com.legalease.LegalEaseSB.Repos;

import com.legalease.LegalEaseSB.Models.Requests;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends MongoRepository<Requests, String>
{

}
