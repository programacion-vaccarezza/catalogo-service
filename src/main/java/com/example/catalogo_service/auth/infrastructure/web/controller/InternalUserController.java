package com.example.catalogo_service.auth.infrastructure.web.controller;

import com.example.catalogo_service.auth.application.exception.UserNotFoundException;
import com.example.catalogo_service.auth.application.service.AuthService;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.infrastructure.web.dto.InternalUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final AuthService authService;

    @Operation(summary = "Obtener nombre y apellido de un usuario por su login (uso exclusivo de servicios internos)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "403", description = "El token no tiene permisos de servicio"),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese login")
    })
    @GetMapping("/{login}")
    public ResponseEntity<InternalUserResponse> getUserByLogin(@PathVariable String login) {
        try {
            User user = authService.getUserByLogin(login);
            return ResponseEntity.ok(InternalUserResponse.builder()
                    .login(user.getLogin())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build());
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
