package com.example.catalogo_service.auth.infrastructure.security.adapter;

import com.example.catalogo_service.auth.domain.model.User;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class JjwtTokenProviderAdapterTest {

    private static final String SECRET_DE_PRUEBA = "YJ7eO3pwjdp/SMz58x4JzCDPadAy9pN+cyem/yldBxs=";
    private static final String OTRO_SECRET_DE_PRUEBA = "vulgnzl0d8iHf/YSu8uysigabZN8vygf76/zFIEJJP8=";

    private final JjwtTokenProviderAdapter tokenProvider =
            new JjwtTokenProviderAdapter(SECRET_DE_PRUEBA, Duration.ofHours(1));

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
        JjwtTokenProviderAdapter otroProveedor =
                new JjwtTokenProviderAdapter(OTRO_SECRET_DE_PRUEBA, Duration.ofHours(1));
        User user = User.builder().login("juan.perez").build();
        String tokenFirmadoConOtraClave = otroProveedor.generateToken(user);

        boolean valido = tokenProvider.isTokenValid(tokenFirmadoConOtraClave);

        assertThat(valido).isFalse();
    }

    @Test
    void debeSerInvalido_cuandoElTokenYaVencio() throws InterruptedException {
        JjwtTokenProviderAdapter proveedorConVencimientoCorto =
                new JjwtTokenProviderAdapter(SECRET_DE_PRUEBA, Duration.ofMillis(1));
        User user = User.builder().login("juan.perez").build();
        String token = proveedorConVencimientoCorto.generateToken(user);

        Thread.sleep(50);

        boolean valido = proveedorConVencimientoCorto.isTokenValid(token);

        assertThat(valido).isFalse();
    }
}