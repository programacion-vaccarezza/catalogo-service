# Seguridad

## Autenticación

Dos tipos de JWT, firmados con claves distintas:

- **Usuario final:** se obtiene con login/contraseña (`POST /api/auth/login`). Sin claim de roles.
- **Técnico (servicio):** se obtiene con client credentials (`POST /api/auth/service-token`), vigencia corta (~30 min), con el claim `roles: ["ROLE_SERVICE"]`. Lo usa turnos-service para llamar a `/api/internal/**`.

Las contraseñas se guardan hasheadas con BCrypt, nunca en texto plano ni en logs.

## Autorización

- `/api/auth/**`: públicos.
- `/api/internal/**`: requieren `ROLE_SERVICE`. Un JWT de usuario final nunca tiene ese rol, así que recibe `403`.
- El resto de los endpoints: requieren cualquier JWT válido (de usuario).
- Cada usuario solo puede ver/modificar sus propios datos (esto lo resuelve cada caso de uso, usando el login que viene del JWT, nunca uno recibido por parámetro).

## Otras decisiones

- **CORS:** orígenes permitidos configurables por variable de entorno (`CORS_ALLOWED_ORIGINS`).
- **Secretos:** todos por variable de entorno, nunca hardcodeados ni versionados.

## Limitaciones conocidas

- **Sin HTTPS:** el tráfico entre la app y los servicios no va cifrado en tránsito (ni la contraseña del login ni el JWT). Queda como mejora a futuro; es una limitación.
- **Secreto de usuario compartido entre servicios:** turnos-service necesita el mismo `JWT_SECRET` para validar JWT de usuario localmente. Esto significa que, en teoría, turnos-service podría generar un JWT válido para cualquier usuario. El `SERVICE_JWT_SECRET` (distinto) evita que ese riesgo aplique también al rol de servicio, pero no resuelve el problema de fondo. Mejora futura: usar un esquema de clave asimétrica (RS256) para los JWT de usuario.
