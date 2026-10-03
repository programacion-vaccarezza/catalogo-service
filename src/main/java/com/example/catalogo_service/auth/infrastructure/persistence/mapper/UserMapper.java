package com.example.catalogo_service.auth.infrastructure.persistence.mapper;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        return UserEntity.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .langKey(user.getLangKey())
                .activated(user.isActivated())
                .build();
    }

    public User toDomainModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .imageUrl(entity.getImageUrl())
                .langKey(entity.getLangKey())
                .activated(entity.isActivated())
                .build();
    }
}
