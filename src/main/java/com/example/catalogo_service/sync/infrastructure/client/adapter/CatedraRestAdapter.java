package com.example.catalogo_service.sync.infrastructure.client.adapter;

import com.example.catalogo_service.sync.domain.model.CatalogSnapshot;
import com.example.catalogo_service.sync.domain.ports.out.CatedraRestPort;
import com.example.catalogo_service.sync.infrastructure.client.dto.SnapshotResponse;
import com.example.catalogo_service.sync.infrastructure.client.mapper.SnapshotMapper;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

// Adapter que implementa CatedraRestPort llamando al REST real de la cátedra
@Component
public class CatedraRestAdapter implements CatedraRestPort {

    private static final String SNAPSHOT_PATH = "/api/synchronization/snapshot";
    private static final Duration RESPONSE_TIMEOUT = Duration.ofSeconds(10);

    private final WebClient webClient;
    private final String jwt;
    private final SnapshotMapper snapshotMapper;

    public CatedraRestAdapter(@Value("${catedra.base-url}") String baseUrl,
                               @Value("${catedra.jwt}") String jwt,
                               SnapshotMapper snapshotMapper) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) RESPONSE_TIMEOUT.toMillis())
                .responseTimeout(RESPONSE_TIMEOUT);
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        this.jwt = jwt;
        this.snapshotMapper = snapshotMapper;
    }

    // Pide el snapshot con el JWT de la cuenta de integración y lo mapea a dominio
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
