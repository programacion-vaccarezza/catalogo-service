package com.example.catalogo_service.auth.application.usecases;

import com.example.catalogo_service.auth.application.exception.UserAlreadyExistsException;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.out.PasswordHasher;
import com.example.catalogo_service.auth.domain.ports.out.UserRepository;
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
class RegisterUserUseCaseImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordHasher passwordHasher;

    @InjectMocks
    RegisterUserUseCaseImpl registerUserUseCase;

    @Test
    void debeLanzarExcepcion_cuandoElLoginYaExiste() {
        User user = User.builder().login("juan.perez").password("clave").build();
        when(userRepository.existsByLogin("juan.perez")).thenReturn(true);

        assertThatThrownBy(() -> registerUserUseCase.registerUser(user))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User already exists: juan.perez");

        verify(userRepository, never()).save(any());
    }

    @Test
    void debeLanzarExcepcion_cuandoElEmailYaExiste() {
        User user = User.builder().login("juan.perez").password("clave").email("juan@example.com").build();
        when(userRepository.existsByLogin("juan.perez")).thenReturn(false);
        when(userRepository.existsByEmail("juan@example.com")).thenReturn(true);

        assertThatThrownBy(() -> registerUserUseCase.registerUser(user))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("User already exists: juan@example.com");

        verify(userRepository, never()).save(any());
    }

    @Test
    void noDebeChequearEmail_cuandoElEmailEsNull() {
        User user = User.builder().login("juan.perez").password("clave").email(null).build();
        when(userRepository.existsByLogin("juan.perez")).thenReturn(false);
        when(passwordHasher.hash("clave")).thenReturn("clave-hasheada");
        when(userRepository.save(user)).thenReturn(user);

        registerUserUseCase.registerUser(user);

        verify(userRepository, never()).existsByEmail(any());
    }

    @Test
    void debeHashearLaContrasenaYActivarElUsuario_cuandoElRegistroEsValido() {
        User user = User.builder().login("juan.perez").password("clave-plana").email("juan@example.com").build();
        User usuarioGuardado = User.builder().id(1L).login("juan.perez").build();

        when(userRepository.existsByLogin("juan.perez")).thenReturn(false);
        when(userRepository.existsByEmail("juan@example.com")).thenReturn(false);
        when(passwordHasher.hash("clave-plana")).thenReturn("clave-hasheada");
        when(userRepository.save(user)).thenReturn(usuarioGuardado);

        User resultado = registerUserUseCase.registerUser(user);

        assertThat(user.getPassword()).isEqualTo("clave-hasheada");
        assertThat(user.isActivated()).isTrue();
        assertThat(resultado).isSameAs(usuarioGuardado);
    }

    @Test
    void debeAsignarIdiomaPorDefecto_cuandoLangKeyEsNull() {
        User user = User.builder().login("juan.perez").password("clave").langKey(null).build();
        when(userRepository.existsByLogin("juan.perez")).thenReturn(false);
        when(passwordHasher.hash("clave")).thenReturn("clave-hasheada");
        when(userRepository.save(user)).thenReturn(user);

        registerUserUseCase.registerUser(user);

        assertThat(user.getLangKey()).isEqualTo("es");
    }

    @Test
    void noDebeSobreescribirElIdioma_cuandoLangKeyYaViene() {
        User user = User.builder().login("juan.perez").password("clave").langKey("en").build();
        when(userRepository.existsByLogin("juan.perez")).thenReturn(false);
        when(passwordHasher.hash("clave")).thenReturn("clave-hasheada");
        when(userRepository.save(user)).thenReturn(user);

        registerUserUseCase.registerUser(user);

        assertThat(user.getLangKey()).isEqualTo("en");
    }
}