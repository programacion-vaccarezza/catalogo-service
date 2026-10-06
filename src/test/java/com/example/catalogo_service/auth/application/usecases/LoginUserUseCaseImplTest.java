package com.example.catalogo_service.auth.application.usecases;

import com.example.catalogo_service.auth.application.exception.InvalidCredentialsException;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.out.PasswordHasher;
import com.example.catalogo_service.auth.domain.ports.out.TokenProvider;
import com.example.catalogo_service.auth.domain.ports.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUserUseCaseImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordHasher passwordHasher;

    @Mock
    TokenProvider tokenProvider;

    @InjectMocks
    LoginUserUseCaseImpl loginUserUseCase;

    @Test
    void debeLanzarExcepcion_cuandoElLoginNoExiste() {
        when(userRepository.findByLogin("juan.perez")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginUserUseCase.loginUser("juan.perez", "clave"))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Usuario o contraseña incorrectos");

        verify(tokenProvider, never()).generateToken(any());
    }

    @Test
    void debeLanzarExcepcion_cuandoLaContrasenaNoCoincide() {
        User user = User.builder().login("juan.perez").password("hash-guardado").build();
        when(userRepository.findByLogin("juan.perez")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("clave-incorrecta", "hash-guardado")).thenReturn(false);

        assertThatThrownBy(() -> loginUserUseCase.loginUser("juan.perez", "clave-incorrecta"))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessage("Usuario o contraseña incorrectos");

        verify(tokenProvider, never()).generateToken(any());
    }

    @Test
    void debeDevolverElToken_cuandoLasCredencialesSonCorrectas() {
        User user = User.builder().login("juan.perez").password("hash-guardado").build();
        when(userRepository.findByLogin("juan.perez")).thenReturn(Optional.of(user));
        when(passwordHasher.matches("clave-correcta", "hash-guardado")).thenReturn(true);
        when(tokenProvider.generateToken(user)).thenReturn("token-generado");

        String resultado = loginUserUseCase.loginUser("juan.perez", "clave-correcta");

        assertThat(resultado).isEqualTo("token-generado");
    }
}