package com.example.catalogo_service.sync.application.usecases;

import com.example.catalogo_service.sync.domain.ports.in.ForceFullSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForceFullSyncUseCaseImpl implements ForceFullSyncUseCase {

    private final FullSyncExecutor fullSyncExecutor;

    @Override
    public void sync() {
        fullSyncExecutor.execute();
    }
}
