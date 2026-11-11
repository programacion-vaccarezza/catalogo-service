# Casos de uso

Actores: **Usuario**, **turnos-service**, **Cátedra**, **Sistema** (el propio servicio).

## CU-01: Registrar usuario

- **Actor:** Usuario
- **Precondición:** el login y el email no están en uso.
- **Flujo principal:** el usuario envía login, contraseña y datos personales → el sistema valida que no existan, hashea la contraseña y lo guarda.
- **Postcondición:** el usuario queda registrado y puede iniciar sesión.
- **Excepción:** login o email ya usados → se rechaza el registro.

## CU-02: Iniciar sesión

- **Actor:** Usuario
- **Precondición:** el usuario está registrado.
- **Flujo principal:** el usuario envía login y contraseña → el sistema valida la contraseña contra el hash guardado → genera y devuelve un JWT.
- **Postcondición:** el usuario queda autenticado para usar el resto de la API.
- **Excepción:** usuario o contraseña incorrectos → se rechaza, sin indicar cuál de los dos falló.

## CU-03: Autenticar cuenta técnica de servicio

- **Actor:** turnos-service
- **Precondición:** turnos-service tiene configurado su client id y client secret.
- **Flujo principal:** turnos-service envía sus credenciales técnicas → el sistema las valida → genera y devuelve un JWT técnico de corta duración, con el rol de servicio.
- **Postcondición:** turnos-service puede llamar a los endpoints internos mientras el token sea válido.
- **Excepción:** credenciales incorrectas → se rechaza.

## CU-04: Listar categorías

- **Actor:** Usuario
- **Precondición:** el usuario está autenticado.
- **Flujo principal:** el usuario pide la lista → el sistema devuelve las categorías de la copia local del catálogo.
- **Postcondición:** el usuario ve las categorías disponibles para filtrar su búsqueda.

## CU-05: Buscar profesionales

- **Actor:** Usuario
- **Precondición:** el usuario está autenticado.
- **Flujo principal:** el usuario busca con filtros opcionales (categoría, nombre, habilitado, disponibilidad) → el sistema aplica los filtros sobre la copia local y devuelve los profesionales que coinciden.
- **Postcondición:** el usuario ve los profesionales que le interesan, sin consultar a la cátedra en cada búsqueda.

## CU-06: Consultar detalle de un profesional

- **Actor:** Usuario
- **Precondición:** el usuario está autenticado.
- **Flujo principal:** el usuario pide un profesional por id → el sistema lo busca en la copia local.
- **Postcondición:** el usuario ve el detalle del profesional elegido.
- **Excepción:** no existe ese id → error 404.

## CU-07: Consultar datos de usuario (uso interno)

- **Actor:** turnos-service
- **Precondición:** turnos-service tiene un JWT técnico válido.
- **Flujo principal:** turnos-service pide los datos de un usuario por su login → el sistema valida el rol de servicio y devuelve nombre y apellido.
- **Postcondición:** turnos-service puede identificar al usuario en sus propias reservas.
- **Excepción:** no existe ese login → error 404.

## CU-08: Consultar profesional y sus horarios (uso interno)

- **Actor:** turnos-service
- **Precondición:** turnos-service tiene un JWT técnico válido.
- **Flujo principal:** turnos-service pide el detalle de un profesional y/o sus horarios semanales por id → el sistema valida el rol de servicio y devuelve los datos de la copia local.
- **Postcondición:** turnos-service puede construir la disponibilidad de turnos combinando esto con las ocupaciones de la cátedra.
- **Excepción:** no existe ese profesional → error 404.

## CU-09: Sincronización completa del catálogo

- **Actor:** Sistema (al arrancar)
- **Precondición:** la base local de profesionales está vacía.
- **Flujo principal:** el sistema pide el snapshot completo por REST a la cátedra → guarda categorías, profesionales y horarios → registra la versión del snapshot como versión local.
- **Postcondición:** el catálogo local queda poblado y listo para responder búsquedas.
- **Excepción:** la cátedra no responde → se loguea el error y el servicio sigue arrancando sin catálogo local, en vez de caerse.

## CU-10: Sincronización incremental del catálogo

- **Actor:** Sistema (al recibir un evento `CatalogUpdated` por Kafka)
- **Precondición:** el catálogo local ya fue sincronizado al menos una vez.
- **Flujo principal:** llega el evento con la nueva versión → si ya se procesó ese evento, se ignora → si no, el sistema consulta en Redis los IDs que cambiaron en cada versión intermedia, trae el estado actual de cada uno y lo guarda → avanza la versión local → marca el evento como procesado.
- **Postcondición:** el catálogo local queda al día sin haber vuelto a descargar todo.
- **Excepción:** se detecta una discontinuidad (la versión local quedó fuera de lo que Redis puede reconstruir) o cualquier error inesperado → se ejecuta una sincronización completa (CU-09) en su lugar.
