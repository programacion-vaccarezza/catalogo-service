# Arquitectura

```mermaid
flowchart TB
    App["App Android (KMP)"]

    subgraph Turnos["turnos-service"]
        TS[API]
        TDB[(turnos-db)]
    end

    subgraph Catalogo["catalogo-service"]
        CS[API]
        CDB[(catalogo-db)]
    end

    Catedra["Cátedra (REST, Redis, Kafka)"]

    App --> TS
    App --> CS
    TS --> TDB
    TS -->|API interna, JWT técnico| CS
    CS --> CDB
    TS --> Catedra
    CS --> Catedra
```

- La app Android corre fuera de los contenedores y consume directamente a los dos servicios.
- Cada servicio tiene su propia base, sin acceso cruzado a tablas del otro.
- `turnos-service` llama a `catalogo-service` solo por su API interna (`/api/internal/**`), nunca accede a su base.
- Los dos servicios se integran por separado con la cátedra: `catalogo-service` sincroniza el catálogo (REST, Redis, Kafka); `turnos-service` gestiona reservas (REST, Kafka).

## Organización interna

Cada servicio sigue arquitectura hexagonal con slicing vertical: un paquete por funcionalidad (`auth`, `catalog`, `sync`), cada uno dividido en `domain` (modelo y ports), `application` (casos de uso) e `infrastructure` (JPA, REST, Redis, Kafka). El detalle de cada caso de uso está en `docs/casos-de-uso.md`.
