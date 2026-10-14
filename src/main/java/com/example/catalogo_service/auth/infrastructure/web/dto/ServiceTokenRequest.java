package com.example.catalogo_service.auth.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTokenRequest {

    @NotBlank(message = "El client id es obligatorio")
    private String clientId;

    @NotBlank(message = "El client secret es obligatorio")
    private String clientSecret;
}
