package com.example.catalogo_service.auth.infrastructure.security.filter;

import com.example.catalogo_service.auth.domain.ports.out.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    TokenProvider tokenProvider;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtAuthenticationFilter(tokenProvider);
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void debeAutenticar_cuandoElTokenEsValido() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token-valido");
        when(tokenProvider.isTokenValid("token-valido")).thenReturn(true);
        when(tokenProvider.getLoginFromToken("token-valido")).thenReturn("juan.perez");
        when(tokenProvider.getRolesFromToken("token-valido")).thenReturn(Collections.emptyList());

        filter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getName()).isEqualTo("juan.perez");
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void debeCargarLasAuthorities_cuandoElTokenTieneRoles() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token-de-servicio");
        when(tokenProvider.isTokenValid("token-de-servicio")).thenReturn(true);
        when(tokenProvider.getLoginFromToken("token-de-servicio")).thenReturn("turnos-service");
        when(tokenProvider.getRolesFromToken("token-de-servicio")).thenReturn(List.of("ROLE_SERVICE"));

        filter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_SERVICE");
    }

    @Test
    void debeAutenticarSinAuthorities_cuandoElTokenNoTraeRoles() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token-sin-roles");
        when(tokenProvider.isTokenValid("token-sin-roles")).thenReturn(true);
        when(tokenProvider.getLoginFromToken("token-sin-roles")).thenReturn("juan.perez");
        when(tokenProvider.getRolesFromToken("token-sin-roles")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getAuthorities()).isEmpty();
    }

    @Test
    void noDebeAutenticar_cuandoNoHayHeaderAuthorization() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void noDebeAutenticar_cuandoElHeaderNoTieneElPrefijoBearer() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic algo");

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void noDebeAutenticar_cuandoElTokenEsInvalido() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token-invalido");
        when(tokenProvider.isTokenValid("token-invalido")).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }
}