package com.example.catalogo_service.sync.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "catalog_sync_state")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogSyncStateEntity {

    @Id
    private Long id;

    private long version;
    private Instant updatedAt;
}
