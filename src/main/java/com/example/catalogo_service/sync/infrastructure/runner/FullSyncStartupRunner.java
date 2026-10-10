package com.example.catalogo_service.sync.infrastructure.runner;

import com.example.catalogo_service.sync.domain.ports.in.FullSyncUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FullSyncStartupRunner implements CommandLineRunner {

    private final FullSyncUseCase fullSyncUseCase;

    @Override
    public void run(String... args) {
        fullSyncUseCase.syncIfEmpty();
    }
}
