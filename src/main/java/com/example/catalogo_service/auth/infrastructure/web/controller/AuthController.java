package com.example.catalogo_service.auth.infrastructure.web.controller;

import com.example.catalogo_service.auth.application.exception.UserAlreadyExistsException;
import com.example.catalogo_service.auth.application.service.AuthService;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.infrastructure.web.dto.RegisterRequest;
import com.example.catalogo_service.auth.infrastructure.web.dto.RegisterResponse;
import com.example.catalogo_service.auth.infrastructure.web.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = userDtoMapper.toDomain(registerRequest);
        try {
            User registeredUser = authService.registerUser(user);
            return new ResponseEntity<>(userDtoMapper.toResponse(registeredUser), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
