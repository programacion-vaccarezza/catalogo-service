package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.sync.domain.model.CatalogUpdatedEvent;
import com.example.catalogo_service.sync.domain.ports.in.ApplyIncrementalChangesUseCase;
import com.example.catalogo_service.sync.domain.ports.out.ProcessedEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HandleCatalogUpdatedUseCaseImplTest {

    @Mock
    ProcessedEventRepository processedEventRepository;

    @Mock
    ApplyIncrementalChangesUseCase applyIncrementalChangesUseCase;

    @InjectMocks
    HandleCatalogUpdatedUseCaseImpl handleCatalogUpdatedUseCase;

    @Test
    void debeIgnorarElEvento_cuandoYaFueProcesado() {
        CatalogUpdatedEvent event = CatalogUpdatedEvent.builder()
                .eventId("evt-1")
                .newVersion(5L)
                .occurredAt(Instant.now())
                .source("catedra")
                .build();

        when(processedEventRepository.existsByEventId("evt-1")).thenReturn(true);

        handleCatalogUpdatedUseCase.handle(event);

        verify(applyIncrementalChangesUseCase, never()).applyChanges(anyLong());
        verify(processedEventRepository, never()).markAsProcessed(anyString());
    }

    @Test
    void debeAplicarLosCambiosYMarcarComoProcesado_cuandoElEventoEsNuevo() {
        CatalogUpdatedEvent event = CatalogUpdatedEvent.builder()
                .eventId("evt-2")
                .newVersion(8L)
                .occurredAt(Instant.now())
                .source("catedra")
                .build();

        when(processedEventRepository.existsByEventId("evt-2")).thenReturn(false);

        handleCatalogUpdatedUseCase.handle(event);

        verify(applyIncrementalChangesUseCase).applyChanges(8L);
        verify(processedEventRepository).markAsProcessed("evt-2");
    }
}
