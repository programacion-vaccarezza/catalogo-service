# Modelo de datos

Base propia (`catalogo_db`), de uso exclusivo de catalogo-service. Ningún otro servicio accede a estas tablas directamente. turnos-service solo puede leer estos datos a través de la API interna (`docs/contratos.md`).

```mermaid
erDiagram
    CATEGORIES ||--o{ PROFESSIONALS : tiene
    PROFESSIONALS ||--o{ WEEKLY_SCHEDULES : tiene

    USERS {
        bigint id PK
        varchar login
        varchar password
        varchar email
        boolean activated
    }

    CATEGORIES {
        bigint id PK
        varchar name
        boolean enabled
    }

    PROFESSIONALS {
        bigint id PK
        bigint category_id FK
        varchar first_name
        varchar last_name
        boolean enabled
    }

    WEEKLY_SCHEDULES {
        bigint id PK
        bigint professional_id FK
        varchar day_of_week
        time start_time
        time end_time
        int slot_duration_minutes
        boolean enabled
    }

    CATALOG_SYNC_STATE {
        bigint id PK
        bigint version
        timestamp updated_at
    }

    PROCESSED_EVENTS {
        varchar event_id PK
        timestamp processed_at
    }
```

- `users`: cuentas de usuario final (login, contraseña hasheada, datos de registro).
- `categories`, `professionals`, `weekly_schedules`: copia local del catálogo publicado por la cátedra. Los `id` son los mismos que usa la cátedra, para poder actualizar por ID en la sincronización incremental.
- `catalog_sync_state`: la versión del catálogo aplicada localmente.
- `processed_events`: eventos de Kafka ya procesados, para idempotencia.
