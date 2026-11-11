package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.sync.domain.model.CatalogSyncState;
import com.example.catalogo_service.sync.domain.ports.in.ForceFullSyncUseCase;
import com.example.catalogo_service.sync.domain.ports.out.CatalogSyncStateRepository;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRedisPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyIncrementalChangesUseCaseImplTest {

    @Mock
    CatalogSyncStateRepository catalogSyncStateRepository;

    @Mock
    CatalogVersionApplier catalogVersionApplier;

    @Mock
    CatedraRedisPort catedraRedisPort;

    @Mock
    ForceFullSyncUseCase forceFullSyncUseCase;

    @InjectMocks
    ApplyIncrementalChangesUseCaseImpl applyIncrementalChangesUseCase;

    @Test
    void noDebeHacerNada_cuandoLaVersionLocalYaAlcanzoLaVersionObjetivo() {
        when(catalogSyncStateRepository.find())
                .thenReturn(Optional.of(CatalogSyncState.builder().version(10L).updatedAt(Instant.now()).build()));

        applyIncrementalChangesUseCase.applyChanges(10L);

        verify(catedraRedisPort, never()).fetchOldestAvailableVersion();
        verify(catalogVersionApplier, never()).applyVersion(anyLong());
        verify(forceFullSyncUseCase, never()).sync();
    }

    @Test
    void debeReconstruirConSincronizacionCompleta_cuandoHayDiscontinuidad() {
        when(catalogSyncStateRepository.find())
                .thenReturn(Optional.of(CatalogSyncState.builder().version(3L).updatedAt(Instant.now()).build()));
        when(catedraRedisPort.fetchOldestAvailableVersion()).thenReturn(5L);

        applyIncrementalChangesUseCase.applyChanges(8L);

        verify(forceFullSyncUseCase).sync();
        verify(catalogVersionApplier, never()).applyVersion(anyLong());
    }

    @Test
    void debeAplicarCadaVersionEnOrden_cuandoNoHayDiscontinuidad() {
        when(catalogSyncStateRepository.find())
                .thenReturn(Optional.of(CatalogSyncState.builder().version(3L).updatedAt(Instant.now()).build()));
        when(catedraRedisPort.fetchOldestAvailableVersion()).thenReturn(1L);

        applyIncrementalChangesUseCase.applyChanges(6L);

        verify(catalogVersionApplier).applyVersion(4L);
        verify(catalogVersionApplier).applyVersion(5L);
        verify(catalogVersionApplier).applyVersion(6L);
        verify(forceFullSyncUseCase, never()).sync();
    }

    @Test
    void debeUsarVersionLocalCero_cuandoNoHayEstadoDeSincronizacionPrevio() {
        when(catalogSyncStateRepository.find()).thenReturn(Optional.empty());
        when(catedraRedisPort.fetchOldestAvailableVersion()).thenReturn(0L);

        applyIncrementalChangesUseCase.applyChanges(2L);

        verify(catalogVersionApplier).applyVersion(1L);
        verify(catalogVersionApplier).applyVersion(2L);
    }

    @Test
    void debeReconstruirConSincronizacionCompleta_cuandoFallaLaAplicacionDeUnaVersion() {
        when(catalogSyncStateRepository.find())
                .thenReturn(Optional.of(CatalogSyncState.builder().version(3L).updatedAt(Instant.now()).build()));
        when(catedraRedisPort.fetchOldestAvailableVersion()).thenReturn(1L);
        doThrow(new IllegalStateException("Error al parsear JSON de Redis"))
                .when(catalogVersionApplier).applyVersion(4L);

        applyIncrementalChangesUseCase.applyChanges(6L);

        verify(forceFullSyncUseCase).sync();
    }
}
