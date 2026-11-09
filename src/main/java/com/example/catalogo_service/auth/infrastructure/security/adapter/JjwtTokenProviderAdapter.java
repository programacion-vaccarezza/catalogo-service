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

// Adapter que implementa TokenProvider con la librería jjwt, usando dos claves distintas (usuario y servicio)
@Component
public class JjwtTokenProviderAdapter implements TokenProvider {

    private static final String ROLES_CLAIM = "roles";
    private static final String ROLE_SERVICE = "ROLE_SERVICE";

    private final SecretKey userKey;
    private final Duration userExpiration;
    private final SecretKey serviceKey;
    private final Duration serviceExpiration;

    // Decodifica las dos claves (base64) desde la configuración
    public JjwtTokenProviderAdapter(@Value("${jwt.secret}") String userSecret,
                                     @Value("${jwt.expiration}") Duration userExpiration,
                                     @Value("${service.jwt-secret}") String serviceSecret,
                                     @Value("${service.jwt-expiration}") Duration serviceExpiration) {
        this.userKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(userSecret));
        this.userExpiration = userExpiration;
        this.serviceKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(serviceSecret));
        this.serviceExpiration = serviceExpiration;
    }

    // Genera el JWT de un usuario final, sin claim de roles, firmado con la clave de usuario
    @Override
    public String generateToken(User user) {
        return buildToken(user.getLogin(), userExpiration, userKey, null);
    }

    // Genera el JWT de una cuenta técnica, con el rol ROLE_SERVICE, firmado con la clave de servicio
    @Override
    public String generateServiceToken(String subject) {
        return buildToken(subject, serviceExpiration, serviceKey, List.of(ROLE_SERVICE));
    }

    // Extrae el subject (login) con la clave de usuario
    @Override
    public String getLoginFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // Solo confía en los roles si el token fue firmado con la clave de servicio: evita que un JWT
    // de usuario final (firmado con otra clave) pueda inventarse un rol
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(serviceKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            List<String> roles = (List<String>) claims.get(ROLES_CLAIM, List.class);
            return roles != null ? roles : Collections.emptyList();
        } catch (JwtException | IllegalArgumentException serviceKeyFailure) {
            return Collections.emptyList();
        }
    }

    // Válido si la firma y la vigencia son válidos
    @Override
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Arma y firma el token; roles es null para los de usuario final
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

    // Prueba la clave de usuario primero y recién si falla prueba la de servicio: ni esto ni
    // getLoginFromToken otorgan permisos por sí solos, por eso alcanza con validar contra cualquiera
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
