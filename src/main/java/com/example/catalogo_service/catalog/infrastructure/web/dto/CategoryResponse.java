package com.example.catalogo_service.catalog.infrastructure.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private boolean enabled;
}
