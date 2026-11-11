package com.example.catalogo_service.sync.application.usecases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ForceFullSyncUseCaseImplTest {

    @Mock
    FullSyncExecutor fullSyncExecutor;

    @InjectMocks
    ForceFullSyncUseCaseImpl forceFullSyncUseCase;

    @Test
    void debeEjecutarSiempreLaSincronizacionCompleta_sinImportarElEstadoLocal() {
        forceFullSyncUseCase.sync();

        verify(fullSyncExecutor).execute();
    }
}
