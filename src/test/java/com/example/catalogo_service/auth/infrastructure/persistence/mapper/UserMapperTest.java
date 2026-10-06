package com.example.catalogo_service.auth.infrastructure.persistence.mapper;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void debeConvertirAEntity_cuandoElUsuarioEsValido() {
        User user = User.builder()
                .id(1L)
                .login("juan.perez")
                .password("hash123")
                .firstName("Juan")
                .lastName("Perez")
                .email("juan.perez@example.com")
                .imageUrl("http://image.url")
                .langKey("es")
                .activated(true)
                .build();

        UserEntity entity = mapper.toEntity(user);

        assertThat(entity).isNotNull();
        assertThat(entity)
                .extracting("id", "login", "password", "firstName", "lastName", "email", "imageUrl", "langKey", "activated")
                .containsExactly(1L, "juan.perez", "hash123", "Juan", "Perez", "juan.perez@example.com", "http://image.url", "es", true);
    }

    @Test
    void debeDevolverNull_cuandoElUsuarioATransformarEsNull() {
        UserEntity entity = mapper.toEntity(null);

        assertThat(entity).isNull();
    }

    @Test
    void debeConvertirADominio_cuandoLaEntidadEsValida() {
        UserEntity entity = UserEntity.builder()
                .id(2L)
                .login("ana.gomez")
                .password("hash456")
                .firstName("Ana")
                .lastName("Gomez")
                .email("ana.gomez@example.com")
                .imageUrl(null)
                .langKey("es")
                .activated(false)
                .build();

        User user = mapper.toDomainModel(entity);

        assertThat(user).isNotNull();
        assertThat(user)
                .extracting("id", "login", "password", "firstName", "lastName", "email", "imageUrl", "langKey", "activated")
                .containsExactly(2L, "ana.gomez", "hash456", "Ana", "Gomez", "ana.gomez@example.com", null, "es", false);
    }

    @Test
    void debeDevolverNull_cuandoLaEntidadATransformarEsNull() {
        User user = mapper.toDomainModel(null);

        assertThat(user).isNull();
    }
}