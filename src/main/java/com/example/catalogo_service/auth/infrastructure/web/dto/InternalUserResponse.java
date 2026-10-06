package com.example.catalogo_service.auth.infrastructure.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalUserResponse {
    private String login;
    private String firstName;
    private String lastName;
}
