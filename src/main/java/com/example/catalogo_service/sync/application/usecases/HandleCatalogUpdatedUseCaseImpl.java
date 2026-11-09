package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.sync.domain.model.CatalogUpdatedEvent;
import com.example.catalogo_service.sync.domain.ports.in.ApplyIncrementalChangesUseCase;
import com.example.catalogo_service.sync.domain.ports.in.HandleCatalogUpdatedUseCase;
import com.example.catalogo_service.sync.domain.ports.out.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Procesa el evento CatalogUpdated de Kafka de forma idempotente
@Component
@RequiredArgsConstructor
public class HandleCatalogUpdatedUseCaseImpl implements HandleCatalogUpdatedUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(HandleCatalogUpdatedUseCaseImpl.class);

    private final ProcessedEventRepository processedEventRepository;
    private final ApplyIncrementalChangesUseCase applyIncrementalChangesUseCase;

    // Si el eventId ya se procesó lo ignora si no aplica los cambios y lo marca procesado
    @Override
    public void handle(CatalogUpdatedEvent event) {
        if (processedEventRepository.existsByEventId(event.getEventId())) {
            LOG.debug("Evento {} ya fue procesado, se ignora", event.getEventId());
            return;
        }

        applyIncrementalChangesUseCase.applyChanges(event.getNewVersion());
        processedEventRepository.markAsProcessed(event.getEventId());
    }
}
