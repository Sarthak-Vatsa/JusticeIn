package com.legalease.LegalEaseSB.Services;

import com.legalease.LegalEaseSB.Models.Lawyers;
import com.legalease.LegalEaseSB.Repos.LawyerRepo;
import com.legalease.LegalEaseSB.config.LawyerPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("lawyerDetailsService")
public class LawyerUserDetailsService implements UserDetailsService {

    @Autowired
    private LawyerRepo lawyerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Lawyers lawyer = lawyerRepository.findByUsername(username);
        if (lawyer != null) {
            return new LawyerPrincipal(lawyer); // implements UserDetails
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}

