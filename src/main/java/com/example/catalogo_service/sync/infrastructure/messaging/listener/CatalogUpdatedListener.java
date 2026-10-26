package com.example.catalogo_service.sync.infrastructure.messaging.listener;

import com.example.catalogo_service.sync.domain.ports.in.HandleCatalogUpdatedUseCase;
import com.example.catalogo_service.sync.infrastructure.messaging.dto.CatalogUpdatedEventDto;
import com.example.catalogo_service.sync.infrastructure.messaging.mapper.CatalogUpdatedEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogUpdatedListener {

    private final HandleCatalogUpdatedUseCase handleCatalogUpdatedUseCase;
    private final CatalogUpdatedEventMapper catalogUpdatedEventMapper;

    @KafkaListener(topics = "${catedra.kafka.catalog-topic}")
    public void onMessage(CatalogUpdatedEventDto dto) {
        handleCatalogUpdatedUseCase.handle(catalogUpdatedEventMapper.toDomainModel(dto));
    }
}
