# Pruebas

## Estrategia

Tests unitarios con JUnit 5 + Mockito + AssertJ: cada test mockea los ports/dependencias de la clase bajo prueba. Cubren:

- Registro, login y autenticación de la cuenta técnica.
- Búsqueda y consulta de profesionales y categorías.
- Sincronización completa e incremental, y el fallback a completa ante discontinuidad o error.
- Idempotencia del consumer de Kafka (mismo `eventId` procesado una sola vez).
- Mappers (entidad ⇄ dominio, DTO ⇄ dominio), incluyendo los casos con `null`.
- Autorización por rol en el filtro JWT.

## Cómo ejecutarlas

Con Docker corriendo (hace falta la base real, no se usa H2 ni mocks de base):

```
docker compose -f compose.service.yaml up -d catalogo-db
./mvnw test
```