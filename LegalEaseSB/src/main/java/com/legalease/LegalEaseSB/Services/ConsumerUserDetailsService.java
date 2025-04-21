package com.legalease.LegalEaseSB.Services;

import com.legalease.LegalEaseSB.Models.Consumers;
import com.legalease.LegalEaseSB.Repos.ConsumerRepo;
import com.legalease.LegalEaseSB.Config.ConsumerPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("consumerDetailsService")
public class ConsumerUserDetailsService implements UserDetailsService {

    @Autowired
    private ConsumerRepo consumerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Consumers consumer = consumerRepository.findByUsername(username);

        if (consumer != null) {
            return new ConsumerPrincipal(consumer); // implements UserDetails
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}

