package com.charity_hub.accounts.shell.controllers.authconfig;

import com.charity_hub.accounts.shell.controllers.common.AccessTokenPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtVerifier jwtVerifier;
    private final ObjectMapper mapper;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public JwtAuthFilter(JwtVerifier jwtVerifier, ObjectMapper mapper) {
        this.jwtVerifier = jwtVerifier;
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = resolveTokenFromAuthHeader(request);

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            var claims = jwtVerifier.verify(token);
            log.info("Token verified successfully");

            var payload = AccessTokenPayload.fromPayload(claims);
            var authentication = new UsernamePasswordAuthenticationToken(
                payload,
                null,
                payload.getPermissions().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList())
            );
            
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("Error processing JWT token", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("description", "Invalid token");
            
            mapper.writeValue(response.getWriter(), errorResponse);
        }
    }

    private String resolveTokenFromAuthHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}