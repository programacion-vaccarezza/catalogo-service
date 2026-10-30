package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.catalog.domain.ports.out.ProfessionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FullSyncUseCaseImplTest {

    @Mock
    ProfessionalRepository professionalRepository;

    @Mock
    FullSyncExecutor fullSyncExecutor;

    @InjectMocks
    FullSyncUseCaseImpl fullSyncUseCase;

    @Test
    void debeOmitirLaSincronizacion_cuandoYaHayProfesionalesLocales() {
        when(professionalRepository.existsAny()).thenReturn(true);

        fullSyncUseCase.syncIfEmpty();

        verify(fullSyncExecutor, never()).execute();
    }

    @Test
    void debeEjecutarLaSincronizacionCompleta_cuandoElCatalogoLocalEstaVacio() {
        when(professionalRepository.existsAny()).thenReturn(false);

        fullSyncUseCase.syncIfEmpty();

        verify(fullSyncExecutor).execute();
    }
}