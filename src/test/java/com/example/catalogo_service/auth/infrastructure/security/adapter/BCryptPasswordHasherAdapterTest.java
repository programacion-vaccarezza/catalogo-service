package com.example.catalogo_service.auth.infrastructure.security.adapter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BCryptPasswordHasherAdapterTest {

    private final BCryptPasswordHasherAdapter hasher = new BCryptPasswordHasherAdapter();

    @Test
    void debeGenerarUnHashDistintoAlTextoPlano_cuandoSeHasheaUnaContrasena() {
        String hash = hasher.hash("una-clave-segura");

        assertThat(hash)
                .isNotNull()
                .isNotEqualTo("una-clave-segura");
    }

    @Test
    void debeGenerarHashesDistintos_cuandoSeHasheaLaMismaContrasenaDosVeces() {
        String hash1 = hasher.hash("una-clave-segura");
        String hash2 = hasher.hash("una-clave-segura");

        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void debeRetornarTrue_cuandoLaContrasenaCoincideConSuHash() {
        String hash = hasher.hash("una-clave-segura");

        boolean resultado = hasher.matches("una-clave-segura", hash);

        assertThat(resultado).isTrue();
    }

    @Test
    void debeRetornarFalse_cuandoLaContrasenaNoCoincideConElHash() {
        String hash = hasher.hash("una-clave-segura");

        boolean resultado = hasher.matches("otra-clave", hash);

        assertThat(resultado).isFalse();
    }
}