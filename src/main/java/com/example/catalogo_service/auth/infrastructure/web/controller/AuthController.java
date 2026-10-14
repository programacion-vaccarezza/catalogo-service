package com.example.catalogo_service.auth.infrastructure.web.controller;

import com.example.catalogo_service.auth.application.exception.InvalidCredentialsException;
import com.example.catalogo_service.auth.application.exception.UserAlreadyExistsException;
import com.example.catalogo_service.auth.application.service.AuthService;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.infrastructure.web.dto.LoginRequest;
import com.example.catalogo_service.auth.infrastructure.web.dto.LoginResponse;
import com.example.catalogo_service.auth.infrastructure.web.dto.RegisterRequest;
import com.example.catalogo_service.auth.infrastructure.web.dto.RegisterResponse;
import com.example.catalogo_service.auth.infrastructure.web.dto.ServiceTokenRequest;
import com.example.catalogo_service.auth.infrastructure.web.dto.ServiceTokenResponse;
import com.example.catalogo_service.auth.infrastructure.web.mapper.UserDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Registrar un nuevo usuario final")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de registro inválidos"),
            @ApiResponse(responseCode = "409", description = "El login o el email ya están en uso")
    })
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

    @Operation(summary = "Autenticar un usuario y obtener un JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso, devuelve el JWT"),
            @ApiResponse(responseCode = "400", description = "Datos de login inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuario o contraseña incorrectos")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.loginUser(loginRequest.getLogin(), loginRequest.getPassword());
            return ResponseEntity.ok(LoginResponse.builder().token(token).build());
        } catch (InvalidCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @Operation(summary = "Autenticar una cuenta técnica de servicio (ej. turnos-service) y obtener un JWT técnico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, devuelve el JWT técnico"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Client id o client secret incorrectos")
    })
    @PostMapping("/service-token")
    public ResponseEntity<ServiceTokenResponse> serviceToken(@Valid @RequestBody ServiceTokenRequest serviceTokenRequest) {
        try {
            String token = authService.authenticateService(serviceTokenRequest.getClientId(), serviceTokenRequest.getClientSecret());
            return ResponseEntity.ok(ServiceTokenResponse.builder().token(token).build());
        } catch (InvalidCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
