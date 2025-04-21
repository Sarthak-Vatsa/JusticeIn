package com.legalease.LegalEaseSB.Config;

import com.legalease.LegalEaseSB.OAuth.CustomOAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    @Qualifier("consumerDetailsService")
    private UserDetailsService consumerUserDetailsService;

    @Autowired
    @Qualifier("lawyerDetailsService")
    private UserDetailsService lawyerUserDetailsService;

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Bean
    public AuthenticationProvider consumerAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(consumerUserDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationProvider lawyerAuthProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(lawyerUserDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/consumer/register",
                                "/consumer/login",
                                "/lawyer/login",
                                "/lawyer/register",
                                "/oauth2/**",                        // allow Google login redirect
                                "/login/oauth2/**").permitAll()                   // allow OAuth2 response handling                        //.requestMatchers("/consumer/**").hasAuthority("ROLE_CONSUMER")

                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(oauth -> oauth
                        .loginPage("/oauth2/authorization/google")   // optional if using default
                        .successHandler(customOAuth2SuccessHandler)   // your custom handler to generate JWT
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
