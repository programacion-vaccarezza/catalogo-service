package com.example.catalogo_service.sync.infrastructure.redis.adapter;

import com.example.catalogo_service.catalog.domain.model.Category;
import com.example.catalogo_service.catalog.domain.model.Professional;
import com.example.catalogo_service.catalog.domain.model.WeeklySchedule;
import com.example.catalogo_service.sync.domain.model.CatalogChangeSet;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRedisPort;
import com.example.catalogo_service.sync.infrastructure.client.dto.CategorySnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.ProfessionalSnapshotDto;
import com.example.catalogo_service.sync.infrastructure.client.dto.WeeklyScheduleSnapshotDto;
import com.example.catalogo_service.sync.infrastructure.redis.dto.CatalogChangesDto;
import com.example.catalogo_service.sync.infrastructure.redis.mapper.CatedraRedisMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CatedraRedisAdapter implements CatedraRedisPort {

    private static final String CHANGES_KEY_PREFIX = "catedra:sync:changes:";
    private static final String CATEGORIES_HASH = "catedra:sync:professional-categories";
    private static final String PROFESSIONALS_HASH = "catedra:sync:professionals";
    private static final String WEEKLY_SCHEDULES_HASH = "catedra:sync:weekly-schedules";

    private final ReactiveStringRedisTemplate catedraRedisTemplate;
    private final ObjectMapper objectMapper;
    private final CatedraRedisMapper catedraRedisMapper;

    @Override
    public CatalogChangeSet fetchChanges(long version) {
        String json = catedraRedisTemplate.opsForValue().get(CHANGES_KEY_PREFIX + version).block();
        CatalogChangesDto dto = readValue(json, CatalogChangesDto.class);
        return catedraRedisMapper.toDomainModel(dto);
    }

    @Override
    public Optional<Category> fetchCategory(Long id) {
        String json = catedraRedisTemplate.<String, String>opsForHash().get(CATEGORIES_HASH, String.valueOf(id)).block();
        return Optional.ofNullable(json)
                .map(value -> readValue(value, CategorySnapshotDto.class))
                .map(catedraRedisMapper::toDomainModel);
    }

    @Override
    public Optional<Professional> fetchProfessional(Long id) {
        String json = catedraRedisTemplate.<String, String>opsForHash().get(PROFESSIONALS_HASH, String.valueOf(id)).block();
        return Optional.ofNullable(json)
                .map(value -> readValue(value, ProfessionalSnapshotDto.class))
                .map(catedraRedisMapper::toDomainModel);
    }

    @Override
    public Optional<WeeklySchedule> fetchWeeklySchedule(Long id) {
        String json = catedraRedisTemplate.<String, String>opsForHash().get(WEEKLY_SCHEDULES_HASH, String.valueOf(id)).block();
        return Optional.ofNullable(json)
                .map(value -> readValue(value, WeeklyScheduleSnapshotDto.class))
                .map(catedraRedisMapper::toDomainModel);
    }

    private <T> T readValue(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error al parsear JSON de Redis para " + type.getSimpleName(), e);
        }
    }
}
