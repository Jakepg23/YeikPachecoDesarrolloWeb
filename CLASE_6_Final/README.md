# Clase 4 Base — Versión Final con CRUD Completo (Clase 5)

Proyecto Spring Boot con **CRUD completo de Cursos** después de aplicar los cambios de la Clase 5.

## ✅ Lo que tiene aplicado de Clase 5

- **CRUD completo** sobre la entidad `Curso`:
  - `GET  /cursos` — listar
  - `GET  /cursos/{id}` — detalle
  - `GET  /cursos/nuevo` — formulario CREATE
  - `POST /cursos` — guardar nuevo
  - `GET  /cursos/{id}/editar` — formulario UPDATE
  - `POST /cursos/{id}` — actualizar
  - `POST /cursos/{id}/eliminar` — borrar
- **Validaciones** con `@NotBlank`, `@Min(1)`, `@Max(8)`, `@Size`.
- **`@Valid` + `BindingResult`** en el Controller.
- **Errores en vista** con `is-invalid`, `th:errors` y `#fields`.
- **Modal de confirmación** de Bootstrap para Delete (no `confirm()` nativo).
- **Toast de feedback** que aparece y se va solo.
- **Flash Messages** con `RedirectAttributes.addFlashAttribute`.
- **Firebase Storage** (bonus) integrado opcionalmente para subir imágenes.
- **Botones** Editar/Eliminar/Ver en cada card.
- Botón **"+ Nuevo curso"** en el listado.

## 🗂️ Estructura

```
Clase_4_Base_Final/
├── pom.xml                                    (con validation + firebase-admin)
├── README.md
├── .gitignore
└── src/main/
    ├── java/com/ufide/clase4base/
    │   ├── Clase4baseApplication.java
    │   ├── controller/
    │   │   ├── HomeController.java
    │   │   └── CursoController.java           ← CRUD completo
    │   ├── entity/
    │   │   └── Curso.java                     ← con validaciones + imagenUrl
    │   ├── repository/
    │   │   └── CursoRepository.java
    │   └── service/
    │       ├── CursoService.java
    │       └── FirebaseService.java           ← bonus
    └── resources/
        ├── application.properties             ← MySQL + multipart + firebase
        ├── static/css/styles.css
        └── templates/
            ├── home.html
            ├── cursos.html                    ← con modal + toast + botones
            ├── curso.html                     ← detalle
            ├── cursos/
            │   └── form.html                  ← formulario CREATE/UPDATE
            └── fragments/header.html
```

## 🚀 Cómo correr

### Pre-requisitos
- Java 25
- Maven 3.9+
- MySQL 8

### 1. Crear la BD
```sql
CREATE DATABASE cursoswebdb CHARACTER SET utf8mb4;
```

### 2. Variables de entorno

**Windows PowerShell:**
```powershell
$env:DB_PASSWORD = "tu_password"
```

### 3. Correr
```bash
./mvnw spring-boot:run        # Linux/Mac
mvnw.cmd spring-boot:run      # Windows
```

### 4. Probar
- http://localhost:8080 — home
- http://localhost:8080/cursos — listado con botones de acción
- http://localhost:8080/cursos/nuevo — formulario CREATE
- http://localhost:8080/cursos/1/editar — formulario UPDATE
- Click en Eliminar → aparece el modal de confirmación
- Después de guardar → toast verde de éxito por 3.5 segundos

## 🔥 Activar Firebase (bonus)

Por defecto el FirebaseService queda **desactivado** (el método `init()` lo detecta cuando no encuentra el JSON). El proyecto arranca sin problemas. Para activarlo:

1. Crear proyecto en https://console.firebase.google.com.
2. Activar Storage.
3. **Project Settings → Service accounts → Generate new private key** → descargar JSON.
4. Renombrar a `firebase-key.json` y guardar en `src/main/resources/`.
5. En `application.properties` poner el bucket:
   ```properties
   firebase.bucket=tu-proyecto.appspot.com
   ```
6. Reiniciar la app. Si todo está bien, en la consola sale:
   `[Firebase] inicializado con bucket tu-proyecto.appspot.com`

⚠️ **Nunca subir `firebase-key.json` al repo.** Ya está en `.gitignore`.

## 🆘 Problemas comunes

| Síntoma | Solución |
|---|---|
| `Access denied for user 'root'` | Verificar `DB_PASSWORD` o el default en `application.properties` |
| `Unknown database 'cursoswebdb'` | Crear la BD con el SQL de arriba |
| `BindingResult` no muestra errores | Va INMEDIATAMENTE después de `@Valid @ModelAttribute` |
| UPDATE crea un duplicado | El `<input type="hidden" th:field="*{id}">` debe estar en el form |
| Modal no abre | Verificar que el `<script>` del bundle de Bootstrap esté antes del `</body>` |
| Toast no aparece | El JS de auto-show está al final del HTML |
| Upload no funciona | `enctype="multipart/form-data"` en el `<form>` |
| Firebase no inicializa | Verificar `firebase-key.json` en resources/ y `firebase.bucket` en properties |

## 📌 Notas didácticas

Este proyecto **NO es el punto de partida** — es el **punto de llegada** después del laboratorio de Clase 5.

Para enseñar la clase 5:
1. Iniciar con el proyecto `Clase_4_Base` original (que solo tiene READ).
2. Aplicar las partes A → F del `guion_clase5_cursos.md`.
3. Al final el proyecto debe quedar **igual que éste**.

Usar este `Clase_4_Base_Final` como **referencia** para que los estudiantes comparen su resultado.
