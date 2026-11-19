package com.example.catalogo_service.sync.infrastructure.redis.dto;

import lombok.*;

// Los cambios de una version del catálogo
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogChangesDto {

    private long version;
    private CatalogChangesDetailDto changes;
}
