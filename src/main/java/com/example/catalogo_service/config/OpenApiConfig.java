package com.example.catalogo_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

// Declara el esquema de autenticación JWT en el OpenAPI, para que Swagger UI muestre el botón Authorize
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "catalogo-service", version = "v1",
                description = "Catálogo de categorías, profesionales y horarios, y autenticación de usuarios"),
        security = @SecurityRequirement(name = "bearerAuth"))
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {
}