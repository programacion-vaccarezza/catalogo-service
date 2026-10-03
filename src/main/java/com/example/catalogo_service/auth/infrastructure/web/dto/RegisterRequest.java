package com.example.catalogo_service.auth.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String login;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, max = 100, message = "La contraseña debe tener entre 4 y 100 caracteres")
    private String password;

    @Size(max = 50, message = "El nombre debe tener como máximo 50 caracteres")
    private String firstName;

    @Size(max = 50, message = "El apellido debe tener como máximo 50 caracteres")
    private String lastName;

    @Email(message = "El email debe ser válido")
    @Size(min = 5, max = 254, message = "El email debe tener entre 5 y 254 caracteres")
    private String email;

    private String imageUrl;

    @Size(min = 2, max = 10, message = "El idioma debe tener entre 2 y 10 caracteres")
    private String langKey;
}