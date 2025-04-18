package com.legalease.LegalEaseSB.Repos;

import com.legalease.LegalEaseSB.Models.Consumers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepo extends MongoRepository<Consumers, String>
{

}
