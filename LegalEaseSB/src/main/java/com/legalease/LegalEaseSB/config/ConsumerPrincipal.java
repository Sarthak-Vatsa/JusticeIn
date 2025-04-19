package com.legalease.LegalEaseSB.config;

import com.legalease.LegalEaseSB.Models.Consumers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ConsumerPrincipal implements UserDetails {

    private final Consumers consumer;

    public ConsumerPrincipal(Consumers consumer) {
        this.consumer = consumer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("CONSUMER"));
    }

    @Override
    public String getPassword() {
        return consumer.getPassword();
    }

    @Override
    public String getUsername() {
        return consumer.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Consumers getConsumer() {
        return consumer;
    }
}

