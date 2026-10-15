package com.example.catalogo_service.sync.infrastructure.client.adapter;

import com.example.catalogo_service.sync.domain.model.CatalogSnapshot;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRestPort;
import com.example.catalogo_service.sync.infrastructure.client.dto.SnapshotResponse;
import com.example.catalogo_service.sync.infrastructure.client.mapper.SnapshotMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CatedraRestAdapter implements CatedraRestPort {

    private static final String SNAPSHOT_PATH = "/api/synchronization/snapshot";

    private final WebClient webClient;
    private final String jwt;
    private final SnapshotMapper snapshotMapper;

    public CatedraRestAdapter(@Value("${catedra.base-url}") String baseUrl,
                               @Value("${catedra.jwt}") String jwt,
                               SnapshotMapper snapshotMapper) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.jwt = jwt;
        this.snapshotMapper = snapshotMapper;
    }

    @Override
    public CatalogSnapshot fetchSnapshot() {
        SnapshotResponse response = webClient.get()
                .uri(SNAPSHOT_PATH)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .retrieve()
                .bodyToMono(SnapshotResponse.class)
                .block();
        return snapshotMapper.toDomainModel(response);
    }
}
