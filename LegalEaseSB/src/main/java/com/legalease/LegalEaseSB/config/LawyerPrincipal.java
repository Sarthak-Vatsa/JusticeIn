package com.legalease.LegalEaseSB.config;

import com.legalease.LegalEaseSB.Models.Lawyers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class LawyerPrincipal implements UserDetails {

    private final Lawyers lawyer;

    public LawyerPrincipal(Lawyers lawyer) {
        this.lawyer = lawyer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("LAWYER"));
    }

    @Override
    public String getPassword() {
        return lawyer.getPassword();
    }

    @Override
    public String getUsername() {
        return lawyer.getUsername();
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

    public Lawyers getLawyer() {
        return lawyer;
    }
}

