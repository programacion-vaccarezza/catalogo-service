package com.example.catalogo_service.auth.infrastructure.web.mapper;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.infrastructure.web.dto.RegisterRequest;
import com.example.catalogo_service.auth.infrastructure.web.dto.RegisterResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserDtoMapperTest {

    private final UserDtoMapper mapper = new UserDtoMapper();

    @Test
    void debeConvertirADominio_cuandoElRequestEsValido() {
        RegisterRequest request = RegisterRequest.builder()
                .login("juan.perez")
                .password("una-clave-segura")
                .firstName("Juan")
                .lastName("Perez")
                .email("juan.perez@example.com")
                .imageUrl("http://image.url")
                .langKey("es")
                .build();

        User user = mapper.toDomain(request);

        assertThat(user).isNotNull();
        assertThat(user)
                .extracting("login", "password", "firstName", "lastName", "email", "imageUrl", "langKey")
                .containsExactly("juan.perez", "una-clave-segura", "Juan", "Perez", "juan.perez@example.com", "http://image.url", "es");
    }

    @Test
    void debeDevolverNull_cuandoElRequestATransformarEsNull() {
        User user = mapper.toDomain(null);

        assertThat(user).isNull();
    }

    @Test
    void debeConvertirAResponse_cuandoElUsuarioEsValido() {
        User user = User.builder()
                .id(1L)
                .login("juan.perez")
                .password("hash-no-deberia-viajar")
                .firstName("Juan")
                .lastName("Perez")
                .email("juan.perez@example.com")
                .imageUrl("http://image.url")
                .langKey("es")
                .activated(true)
                .build();

        RegisterResponse response = mapper.toResponse(user);

        assertThat(response).isNotNull();
        assertThat(response)
                .extracting("id", "login", "firstName", "lastName", "email", "imageUrl", "langKey", "activated")
                .containsExactly(1L, "juan.perez", "Juan", "Perez", "juan.perez@example.com", "http://image.url", "es", true);
    }

    @Test
    void debeDevolverNull_cuandoElUsuarioATransformarEsNull() {
        RegisterResponse response = mapper.toResponse(null);

        assertThat(response).isNull();
    }
}