package com.example.catalogo_service.auth.infrastructure.web.mapper;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.infrastructure.web.dto.RegisterRequest;
import com.example.catalogo_service.auth.infrastructure.web.dto.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public User toDomain(RegisterRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .login(request.getLogin())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .imageUrl(request.getImageUrl())
                .langKey(request.getLangKey())
                .build();
    }

    public RegisterResponse toResponse(User domain) {
        if (domain == null) {
            return null;
        }
        return RegisterResponse.builder()
                .id(domain.getId())
                .login(domain.getLogin())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .email(domain.getEmail())
                .imageUrl(domain.getImageUrl())
                .langKey(domain.getLangKey())
                .activated(domain.isActivated())
                .build();
    }
}
