package com.example.catalogo_service.auth.application.usecases;

import com.example.catalogo_service.auth.application.exception.InvalidCredentialsException;
import com.example.catalogo_service.auth.domain.ports.out.ServiceCredentialsValidator;
import com.example.catalogo_service.auth.domain.ports.out.TokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateServiceUseCaseImplTest {

    @Mock
    ServiceCredentialsValidator serviceCredentialsValidator;

    @Mock
    TokenProvider tokenProvider;

    @InjectMocks
    AuthenticateServiceUseCaseImpl authenticateServiceUseCase;

    @Test
    void debeLanzarExcepcion_cuandoLasCredencialesSonInvalidas() {
        when(serviceCredentialsValidator.isValid("turnos-service", "secreto-incorrecto")).thenReturn(false);

        assertThatThrownBy(() -> authenticateServiceUseCase.authenticateService("turnos-service", "secreto-incorrecto"))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Usuario o contraseña incorrectos");

        verify(tokenProvider, never()).generateServiceToken(any());
    }

    @Test
    void debeDevolverElToken_cuandoLasCredencialesSonValidas() {
        when(serviceCredentialsValidator.isValid("turnos-service", "secreto-correcto")).thenReturn(true);
        when(tokenProvider.generateServiceToken("turnos-service")).thenReturn("token-de-servicio-generado");

        String resultado = authenticateServiceUseCase.authenticateService("turnos-service", "secreto-correcto");

        assertThat(resultado).isEqualTo("token-de-servicio-generado");
    }
}
