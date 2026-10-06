package com.example.catalogo_service.auth.infrastructure.security.adapter;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.out.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JjwtTokenProviderAdapter implements TokenProvider {

    private static final String ROLES_CLAIM = "roles";
    private static final String ROLE_SERVICE = "ROLE_SERVICE";

    private final SecretKey userKey;
    private final Duration userExpiration;
    private final SecretKey serviceKey;
    private final Duration serviceExpiration;

    public JjwtTokenProviderAdapter(@Value("${jwt.secret}") String userSecret,
                                     @Value("${jwt.expiration}") Duration userExpiration,
                                     @Value("${service.jwt-secret}") String serviceSecret,
                                     @Value("${service.jwt-expiration}") Duration serviceExpiration) {
        this.userKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(userSecret));
        this.userExpiration = userExpiration;
        this.serviceKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(serviceSecret));
        this.serviceExpiration = serviceExpiration;
    }

    @Override
    public String generateToken(User user) {
        return buildToken(user.getLogin(), userExpiration, userKey, null);
    }

    @Override
    public String generateServiceToken(String subject) {
        return buildToken(subject, serviceExpiration, serviceKey, List.of(ROLE_SERVICE));
    }

    @Override
    public String getLoginFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        List<String> roles = (List<String>) parseClaims(token).get(ROLES_CLAIM, List.class);
        return roles != null ? roles : Collections.emptyList();
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private String buildToken(String subject, Duration expiration, SecretKey key, List<String> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration.toMillis());

        var builder = Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiry);

        if (roles != null) {
            builder.claim(ROLES_CLAIM, roles);
        }

        return builder.signWith(key).compact();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(userKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException userKeyFailure) {
            return Jwts.parser()
                    .verifyWith(serviceKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }
    }
}
