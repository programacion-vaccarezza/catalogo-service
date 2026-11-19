package com.example.catalogo_service.sync.infrastructure.persistence.adapter;

import com.example.catalogo_service.sync.domain.ports.out.ProcessedEventRepository;
import com.example.catalogo_service.sync.infrastructure.persistence.entity.ProcessedEventEntity;
import com.example.catalogo_service.sync.infrastructure.persistence.repository.JpaProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

// Adapter que implementa ProcessedEventRepository
@Component
@RequiredArgsConstructor
public class JpaProcessedEventRepositoryAdapter implements ProcessedEventRepository {

    private final JpaProcessedEventRepository jpaProcessedEventRepository;

    // Indica si ese eventId ya está guardado
    @Override
    public boolean existsByEventId(String eventId) {
        return jpaProcessedEventRepository.existsById(eventId);
    }

    // Guarda el eventId con la fecha de procesamiento actual
    @Override
    public void markAsProcessed(String eventId) {
        jpaProcessedEventRepository.save(ProcessedEventEntity.builder()
                .eventId(eventId)
                .processedAt(Instant.now())
                .build());
    }
}
