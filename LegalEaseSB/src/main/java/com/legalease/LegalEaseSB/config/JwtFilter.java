package com.legalease.LegalEaseSB.config;

import com.legalease.LegalEaseSB.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext context; // used to dynamically get the correct service bean

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String role = null;

        String path = request.getServletPath();
        System.out.println("Servlet path: " + path);

        if ("/consumer/login".equals(path) || "/consumer/register".equals(path) ||
                "/lawyer/login".equals(path) || "/lawyer/register".equals(path)) {
            System.out.println("Bhaiya ggg");
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
            role = jwtService.extractRole(token); // Use this to pick the service
        }

        System.out.println(role);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = null;

            // Pick the right service based on role
            if ("CONSUMER".equalsIgnoreCase(role)) {
                userDetails = context.getBean("consumerDetailsService", UserDetailsService.class)
                        .loadUserByUsername(username);
            } else if ("LAWYER".equalsIgnoreCase(role)) {
                userDetails = context.getBean("lawyerDetailsService", UserDetailsService.class)
                        .loadUserByUsername(username);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid role in token");
                return;
            }

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
