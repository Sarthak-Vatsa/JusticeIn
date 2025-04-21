package com.legalease.LegalEaseSB.OAuth;

import com.legalease.LegalEaseSB.Models.Consumers;
import com.legalease.LegalEaseSB.Models.Lawyers;
import com.legalease.LegalEaseSB.Repos.ConsumerRepo;
import com.legalease.LegalEaseSB.Repos.LawyerRepo;
import com.legalease.LegalEaseSB.Services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ConsumerRepo consumerRepo;

    @Autowired
    private LawyerRepo lawyerRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();

        String email = (String) attributes.get("email");
        String jwtToken;
        String redirectUrl;

        Consumers consumer = consumerRepo.findByUsername(email);
        Lawyers lawyer = lawyerRepo.findByUsername(email);

        if (consumer != null) {
            //consumer exists
            jwtToken = jwtService.generateToken(email, "Consumer" );
            response.setContentType("application/json");
            response.getWriter().write("{\"token\": \"" + jwtToken + "\"}");

            //can also redirect with token as query param
            //redirectUrl = "http://localhost:3000/oauth2/success?token=" + jwtToken + "&role=consumer";

            return;

        } else if (lawyer != null) {
            //lawyer exists
            jwtToken = jwtService.generateToken(email, "Lawyer");
            response.setContentType("application/json");
            response.getWriter().write("{\"token\": \"" + jwtToken + "\"}");

            //can also redirect with token as query param
            //redirectUrl = "http://localhost:3000/oauth2/success?token=" + jwtToken + "&role=lawyer";

            return;

        } else {
            // new user
            // Redirect to a frontend page where user selects their role (consumer or lawyer)
            redirectUrl = "http://localhost:3000/choose-role?email=" + email + "&username=" + email;
        }

        response.sendRedirect(redirectUrl);
    }
}

