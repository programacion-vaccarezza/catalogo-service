package com.example.catalogo_service.auth.application.usecases;

import com.example.catalogo_service.auth.application.exception.UserNotFoundException;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserByLoginUseCaseImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    GetUserByLoginUseCaseImpl getUserByLoginUseCase;

    @Test
    void debeLanzarExcepcion_cuandoElLoginNoExiste() {
        when(userRepository.findByLogin("juan.perez")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getUserByLoginUseCase.getUserByLogin("juan.perez"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Usuario no encontrado: juan.perez");
    }

    @Test
    void debeDevolverElUsuario_cuandoElLoginExiste() {
        User user = User.builder().login("juan.perez").firstName("Juan").lastName("Perez").build();
        when(userRepository.findByLogin("juan.perez")).thenReturn(Optional.of(user));

        User resultado = getUserByLoginUseCase.getUserByLogin("juan.perez");

        assertThat(resultado).isEqualTo(user);
    }
}
