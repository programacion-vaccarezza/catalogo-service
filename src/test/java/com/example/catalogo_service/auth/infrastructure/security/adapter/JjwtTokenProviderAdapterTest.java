package com.example.catalogo_service.auth.infrastructure.security.adapter;

import com.example.catalogo_service.auth.domain.model.User;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JjwtTokenProviderAdapterTest {

    private static final String SECRET_DE_PRUEBA = "YJ7eO3pwjdp/SMz58x4JzCDPadAy9pN+cyem/yldBxs=";
    private static final String OTRO_SECRET_DE_PRUEBA = "vulgnzl0d8iHf/YSu8uysigabZN8vygf76/zFIEJJP8=";
    private static final String SERVICE_SECRET_DE_PRUEBA = "N7y+yKwLvzWSkTjvlCwL2DRaFyfSiTIx7Mi93vrAeQc=";

    private final JjwtTokenProviderAdapter tokenProvider = new JjwtTokenProviderAdapter(
            SECRET_DE_PRUEBA, Duration.ofHours(1), SERVICE_SECRET_DE_PRUEBA, Duration.ofMinutes(30));

    @Test
    void debeGenerarUnTokenNoNulo_cuandoSeGeneraParaUnUsuario() {
        User user = User.builder().login("juan.perez").build();

        String token = tokenProvider.generateToken(user);

        assertThat(token).isNotNull().isNotBlank();
    }

    @Test
    void debeExtraerElLogin_cuandoElTokenEsValido() {
        User user = User.builder().login("juan.perez").build();
        String token = tokenProvider.generateToken(user);

        String login = tokenProvider.getLoginFromToken(token);

        assertThat(login).isEqualTo("juan.perez");
    }

    @Test
    void debeRetornarListaVacia_cuandoElTokenEsDeUnUsuarioFinal() {
        User user = User.builder().login("juan.perez").build();
        String token = tokenProvider.generateToken(user);

        List<String> roles = tokenProvider.getRolesFromToken(token);

        assertThat(roles).isEmpty();
    }

    @Test
    void debeSerValido_cuandoElTokenFueGeneradoPorElMismoProveedor() {
        User user = User.builder().login("juan.perez").build();
        String token = tokenProvider.generateToken(user);

        boolean valido = tokenProvider.isTokenValid(token);

        assertThat(valido).isTrue();
    }

    @Test
    void debeSerInvalido_cuandoElTokenEstaCorrupto() {
        boolean valido = tokenProvider.isTokenValid("esto-no-es-un-jwt-valido");

        assertThat(valido).isFalse();
    }

    @Test
    void debeSerInvalido_cuandoElTokenFueFirmadoConOtraClave() {
        JjwtTokenProviderAdapter otroProveedor = new JjwtTokenProviderAdapter(
                OTRO_SECRET_DE_PRUEBA, Duration.ofHours(1), SERVICE_SECRET_DE_PRUEBA, Duration.ofMinutes(30));
        User user = User.builder().login("juan.perez").build();
        String tokenFirmadoConOtraClave = otroProveedor.generateToken(user);

        boolean valido = tokenProvider.isTokenValid(tokenFirmadoConOtraClave);

        assertThat(valido).isFalse();
    }

    @Test
    void debeSerInvalido_cuandoElTokenYaVencio() throws InterruptedException {
        JjwtTokenProviderAdapter proveedorConVencimientoCorto = new JjwtTokenProviderAdapter(
                SECRET_DE_PRUEBA, Duration.ofMillis(1), SERVICE_SECRET_DE_PRUEBA, Duration.ofMinutes(30));
        User user = User.builder().login("juan.perez").build();
        String token = proveedorConVencimientoCorto.generateToken(user);

        Thread.sleep(50);

        boolean valido = proveedorConVencimientoCorto.isTokenValid(token);

        assertThat(valido).isFalse();
    }

    @Test
    void debeGenerarUnTokenDeServicioNoNulo_cuandoSeGeneraParaUnSubjectDeServicio() {
        String token = tokenProvider.generateServiceToken("turnos-service");

        assertThat(token).isNotNull().isNotBlank();
    }

    @Test
    void debeExtraerElSubject_cuandoElTokenEsDeServicio() {
        String token = tokenProvider.generateServiceToken("turnos-service");

        String subject = tokenProvider.getLoginFromToken(token);

        assertThat(subject).isEqualTo("turnos-service");
    }

    @Test
    void debeIncluirElRoleService_cuandoElTokenEsDeServicio() {
        String token = tokenProvider.generateServiceToken("turnos-service");

        List<String> roles = tokenProvider.getRolesFromToken(token);

        assertThat(roles).containsExactly("ROLE_SERVICE");
    }

    @Test
    void debeSerValido_cuandoElTokenDeServicioFueGeneradoPorElMismoProveedor() {
        String token = tokenProvider.generateServiceToken("turnos-service");

        boolean valido = tokenProvider.isTokenValid(token);

        assertThat(valido).isTrue();
    }
}
