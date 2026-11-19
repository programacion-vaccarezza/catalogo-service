# catalogo-service

Servicio de catálogo (categorías, profesionales, horarios) y autenticación de usuarios. Sincroniza su copia local del catálogo contra el servicio central de la cátedra (REST, Redis y Kafka).

## Requisitos

- Java 25
- Docker y Docker Compose
- Credenciales de la cuenta de integración con la cátedra (REST, Redis, Kafka)

## Configuración

1. Copiar `.env.example` a `.env`.
2. Completar los valores: credenciales de la base, JWT, cuenta técnica de turnos-service, y los datos de la cátedra.

## Ejecutar el servicio

Con Docker:

```
docker compose -f compose.service.yaml up -d --build
```

Para levantarlo junto con turnos-service:

```
docker compose up -d --build
```

Para bajarlo:

```
docker compose down
```

La API queda en `http://localhost:8080`, y Swagger en `http://localhost:8080/swagger-ui.html`.

## Ejecutar los tests

Con Docker corriendo:

```
docker compose -f compose.service.yaml up -d catalogo-db
./mvnw test
```

## Documentación

Ver `docs/`: casos de uso, arquitectura, contratos, modelo de datos, sincronización, seguridad y pruebas.
