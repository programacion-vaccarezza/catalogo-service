package com.example.catalogo_service.auth.infrastructure.security.adapter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceCredentialsValidatorAdapterTest {

    private final ServiceCredentialsValidatorAdapter validator =
            new ServiceCredentialsValidatorAdapter("turnos-service", "secreto-correcto");

    @Test
    void debeRetornarTrue_cuandoElClientIdYElClientSecretSonCorrectos() {
        boolean resultado = validator.isValid("turnos-service", "secreto-correcto");

        assertThat(resultado).isTrue();
    }

    @Test
    void debeRetornarFalse_cuandoElClientIdEsIncorrecto() {
        boolean resultado = validator.isValid("otro-servicio", "secreto-correcto");

        assertThat(resultado).isFalse();
    }

    @Test
    void debeRetornarFalse_cuandoElClientSecretEsIncorrecto() {
        boolean resultado = validator.isValid("turnos-service", "secreto-incorrecto");

        assertThat(resultado).isFalse();
    }

    @Test
    void debeRetornarFalse_cuandoElClientIdYElClientSecretSonIncorrectos() {
        boolean resultado = validator.isValid("otro-servicio", "secreto-incorrecto");

        assertThat(resultado).isFalse();
    }
}
