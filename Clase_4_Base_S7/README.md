# Clase 4 Base S7 — punto de partida Clase 7

Proyecto Spring Boot con **CRUD completo de Cursos** (post Clase 6) preparado para agregar **asociaciones Hibernate + Aiven cloud** durante la Clase 7.

## Estado del proyecto

Ya está aplicado:

- CRUD completo de Cursos (CREATE, READ, UPDATE, DELETE).
- Validaciones con Bean Validation (`@Valid` + `BindingResult`).
- Modal de confirmación para Delete.
- Toast de feedback.
- Flash Messages con `RedirectAttributes`.
- Firebase Storage opcional (bonus de Clase 6).
- Variables de entorno para la BD local.

## Lo que se agrega en Clase 7

| Parte | Tema | Archivos a tocar |
|---|---|---|
| A | Crear entidad `Profesor` | `entity/Profesor.java` (nuevo, ver `package-info.java`) + `Curso.java` (modificar campo profesor) |
| B | CRUD básico de profesores + select en form de curso | `repository/`, `service/`, `controller/` (nuevos `package-info.java`), `templates/profesores/*` (nuevos, ver `_referencia_profesores_html.txt`) |
| C | Mostrar profesor.nombre en cards de curso | `templates/cursos.html` (modificar) |
| D | **Bonus:** relación inversa `@OneToMany` en Profesor | `Profesor.java` (descomentar segunda parte) |
| E | **Bonus:** consulta JPQL personalizada | `ProfesorRepository.java` (descomentar `@Query`) |
| F | Migrar BD local a Aiven cloud | `application.properties` (descomentar bloque AIVEN) |

## Convención de marcadores

Igual que en Clase 6, cada bloque comentado tiene una etiqueta del tipo:

```
// CLASE 7 - PARTE X.Y: descomentar para ...
```

Para los **archivos a crear** desde cero, el código está en:

- `entity/package-info.java` → contiene `Profesor.java` comentado.
- `repository/package-info.java` → contiene `ProfesorRepository.java` comentado.
- `service/package-info.java` → contiene `ProfesorService.java` comentado.
- `controller/package-info.java` → contiene `ProfesorController.java` comentado.
- `templates/_referencia_profesores_html.txt` → contiene `profesores.html` + `profesores/form.html` + cambios para `cursos/form.html` y `cursos.html`.

## Cómo correr

### Pre-requisitos

- Java 21.
- Maven 3.9+.
- MySQL 8 local **o** cuenta gratis en https://aiven.io.

### Modo local (default)

```sql
CREATE DATABASE cursoswebdb CHARACTER SET utf8mb4;
```

```powershell
setx DB_PASSWORD "tu_password"
```

```bash
./mvnw spring-boot:run        # Linux/Mac
mvnw.cmd spring-boot:run      # Windows
```

### Modo Aiven (al hacer la PARTE F)

1. Crear cuenta gratis en https://aiven.io.
2. Crear servicio **MySQL** (plan free).
3. Copiar del panel de Aiven: Host, Port, User, Password.
4. En `application.properties` comentar el bloque local y descomentar el bloque AIVEN.
5. Definir `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` con los valores de Aiven.
6. Volver a correr la app. Hibernate crea las tablas en el cluster cloud.

## Verificación rápida

- http://localhost:8080/ — home
- http://localhost:8080/cursos — listado (debería seguir funcionando exactamente igual que en S6)
- http://localhost:8080/profesores — listado de profesores (a partir de PARTE B)

## Material complementario

- `Clase_4_Base_S7_Implementado.zip` (en `Recursos_Profesor/`) — versión final con todo descomentado, para comparar el resultado de los estudiantes.
- `guion_clase7.md` — guion del lab guiado paso a paso.
- `SEM7.pptx` — presentación con notas para dar la clase.
