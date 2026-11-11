# Sincronización con la cátedra

## Sincronización completa

Se ejecuta al arrancar, si la base local está vacía.

```mermaid
sequenceDiagram
    participant App as catalogo-service
    participant C as REST cátedra
    App->>C: GET /api/synchronization/snapshot
    C-->>App: categorías, profesionales, horarios + versión
    App->>App: guarda todo + versión local
```

Si la cátedra no responde al arrancar, se loguea el error y el servicio sigue levantado en modo degradado, en vez de caerse.

## Sincronización incremental

Se ejecuta al recibir un evento `CatalogUpdated` por Kafka.

```mermaid
sequenceDiagram
    participant K as Kafka
    participant App as catalogo-service
    participant R as Redis cátedra
    K->>App: CatalogUpdated(eventId, newVersion)
    App->>App: ¿eventId ya procesado?
    alt ya procesado
        App->>App: ignorar (duplicado)
    else evento nuevo
        loop por cada versión entre la local+1 y newVersion
            App->>R: IDs que cambiaron en esa versión
            App->>R: estado actual de cada ID
            App->>App: guarda solo esas entidades + versión
        end
        App->>App: marca el eventId como procesado
    end
```

## Idempotencia y recuperación

- **Mensajes duplicados:** cada evento tiene un `eventId` único. Antes de procesarlo se chequea si ya está guardado en `processed_events`; si ya está, se ignora.
- **Mensajes perdidos o fuera de orden:** si la versión local quedó por debajo de lo que Redis todavía puede reconstruir (discontinuidad), o si ocurre cualquier error al aplicar lo incremental, se ejecuta una sincronización completa en su lugar.