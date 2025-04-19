package com.legalease.LegalEaseSB.Repos;

import com.legalease.LegalEaseSB.Models.Lawyers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawyerRepo extends MongoRepository<Lawyers, String>
{
    Lawyers findByUsername(String username);
    List<Lawyers> findByDescriptionRegexIgnoreCase(String regex);
}
