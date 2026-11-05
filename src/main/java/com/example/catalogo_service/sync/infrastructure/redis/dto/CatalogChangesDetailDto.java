package com.example.catalogo_service.sync.infrastructure.redis.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogChangesDetailDto {

    private List<Long> professionalCategories;
    private List<Long> professionals;
    private List<Long> weeklySchedules;
}
