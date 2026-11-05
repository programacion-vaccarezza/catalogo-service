package com.example.catalogo_service.sync.infrastructure.runner;

import com.example.catalogo_service.sync.domain.ports.in.FullSyncUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FullSyncStartupRunnerTest {

    @Mock
    FullSyncUseCase fullSyncUseCase;

    @InjectMocks
    FullSyncStartupRunner fullSyncStartupRunner;

    @Test
    void debeSincronizarAlArrancar() {
        fullSyncStartupRunner.run();

        verify(fullSyncUseCase).syncIfEmpty();
    }

    @Test
    void noDebePropagarLaExcepcion_cuandoLaSincronizacionFalla() {
        doThrow(new RuntimeException("la catedra no responde")).when(fullSyncUseCase).syncIfEmpty();

        assertThatCode(() -> fullSyncStartupRunner.run()).doesNotThrowAnyException();
    }
}