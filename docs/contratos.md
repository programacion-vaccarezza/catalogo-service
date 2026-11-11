# Contratos entre los dos servicios

turnos-service consume estos endpoints de catalogo-service. Protegidos con un JWT técnico (`ROLE_SERVICE`), distinto del JWT de usuario final.

## Obtener el JWT técnico

```
POST /api/auth/service-token
Body:  { "clientId": "...", "clientSecret": "..." }
200:   { "token": "<JWT>" }
401:   client id o client secret incorrectos
```

## Datos de usuario

```
GET /api/internal/users/{login}
Header: Authorization: Bearer <JWT técnico>

200: { "login": "...", "firstName": "...", "lastName": "..." }
403: el token no tiene ROLE_SERVICE
404: no existe un usuario con ese login
```

## Detalle de un profesional

```
GET /api/internal/professionals/{id}

200: { "id": 1, "categoryId": 1, "firstName": "...", "lastName": "...", "enabled": true }
403: el token no tiene ROLE_SERVICE
404: no existe un profesional con ese id
```

## Horarios semanales de un profesional

```
GET /api/internal/professionals/{id}/schedules

200: [ { "id": 1, "dayOfWeek": "MONDAY", "startTime": "09:00:00", "endTime": "12:00:00", "slotDurationMinutes": 30, "enabled": true }, ... ]
403: el token no tiene ROLE_SERVICE
404: no existe un profesional con ese id
```

## Notas

- Todos devuelven `401` si falta el token o es inválido.
- turnos-service nunca accede a la base de catalogo-service: solo a estos endpoints.
