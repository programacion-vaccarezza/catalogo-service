package com.example.catalogo_service.sync.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogChangeSet {

    private long version;
    private List<Long> categoryIds;
    private List<Long> professionalIds;
    private List<Long> weeklyScheduleIds;
}
