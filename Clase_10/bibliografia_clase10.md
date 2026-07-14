# Bibliografía de repaso — Clase 10: Spring Security 1 (autenticación)

Este documento es para releer después de la clase, con calma, usando como referencia el proyecto `cursosapp` y las slides que ya tenés. No repite el paso a paso del lab (eso está en `lab_clase10.md`) — acá el objetivo es que entiendas **por qué** funciona cada cosa.

---

## 1. Qué protege Spring Security hoy y qué no

Hoy tu app pasa de estar completamente abierta a exigir login para casi todo. Pero **ojo con una confusión común**: aunque ya existe el campo `rol` en `Usuario` (`ADMIN` o `USER`), **todavía no hay ninguna regla que lo use**. Cualquier usuario logueado, sea ADMIN o USER, puede crear, editar y eliminar cursos por igual. Eso se resuelve la próxima clase (S11) con `@PreAuthorize`.

En resumen:
- **S10 (hoy):** ¿quién sos? — login, logout, sesión.
- **S11 (la próxima):** ¿qué podés hacer según tu rol? — autorización.

---

## 2. Cómo se arma la protección: `SecurityFilterChain`

Spring Security se engancha como un **filtro** que revisa cada request ANTES de que llegue a tu Controller. Vos le decís qué reglas aplicar en un bean:

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/css/**").permitAll()
            .anyRequest().authenticated())
        .formLogin(form -> form.loginPage("/login").permitAll())
        .logout(logout -> logout.permitAll());
    return http.build();
}
```

Las reglas de `authorizeHttpRequests` se leen **en orden**: la primera que matchea la URL es la que se aplica. Por eso `/`, `/login` y `/css/**` van marcadas como públicas (`permitAll()`) ANTES de la regla general `anyRequest().authenticated()`, que exige sesión para todo lo demás (incluido `/cursos/**`, aunque no lo veas escrito explícitamente).

---

## 3. `UserDetailsService` — el puente entre tu tabla y Spring Security

Spring Security no sabe nada de tu entidad `Usuario` a menos que se lo digas. `CustomUserDetailsService` es exactamente ese puente:

```java
@Override
public UserDetails loadUserByUsername(String username) {
    Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(...));
    return User.builder()
            .username(usuario.getUsername())
            .password(usuario.getPassword())
            .roles(usuario.getRol())
            .build();
}
```

Cuando alguien intenta loguearse, Spring Security llama a este método solo, compara la contraseña tipeada contra el hash guardado, y si coincide, te deja entrar. Vos no escribís esa comparación a mano — solo le decís CÓMO buscar al usuario.

**Detalle para tener en cuenta:** `.roles("ADMIN")` le agrega el prefijo `ROLE_` por debajo (queda como `ROLE_ADMIN` internamente). No hace falta que vos escribas ese prefijo en la base de datos ni en el código — Spring lo agrega solo. Esto te va a importar la próxima clase cuando veamos `hasRole()` vs `hasAuthority()`.

---

## 4. BCrypt — por qué nunca vas a ver una contraseña en texto plano

BCrypt es un algoritmo de hashing pensado específicamente para contraseñas:

- Es **irreversible**: no existe un "decrypt", solo podés comparar (`matches()`), nunca "leer" la contraseña original a partir del hash.
- Cada hash incluye un **salt aleatorio** — la misma contraseña genera un hash distinto cada vez que la hasheás. Por eso no podés saber si dos usuarios comparten contraseña con solo mirar la tabla.
- Tiene un **costo configurable** (rounds) — se puede hacer más lento a propósito a medida que las computadoras se vuelven más rápidas, para que siga siendo difícil de forzar por fuerza bruta.

En el proyecto, `SecurityConfig` define un bean `PasswordEncoder`:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

Y en `seed-data.sql`, las contraseñas ya vienen como hashes (`$2b$10$...`), nunca como texto plano.

---

## 5. Por qué tus formularios existentes no se rompieron (CSRF)

Spring Security agrega **protección CSRF** por defecto a cualquier `POST`/`PUT`/`DELETE`. En teoría, esto debería romper todos los formularios que ya tenías (crear curso, eliminar curso) — de golpe empezarían a devolver `403`.

No pasó porque Thymeleaf ya venía usando `th:action` en todos los forms (en vez de `action` a secas). Cuando usás `th:action`, Thymeleaf agrega automáticamente un campo oculto con el token CSRF — gratis, sin que tengas que escribir nada. Por eso el botón de "Salir" (logout) también tiene que ser un `<form method="post">` con `th:action`, no un simple link `<a href="/logout">`.

---

## 6. El navbar dinámico: `sec:authorize`

En `fragments/header.html` vas a ver atributos que no habías usado antes:

```html
<li sec:authorize="isAuthenticated()">...</li>
<li sec:authorize="isAnonymous()">...</li>
<span sec:authentication="name">usuario</span>
```

`sec:authorize` muestra u oculta un elemento según una condición de seguridad (¿hay sesión? ¿no hay sesión?). `sec:authentication="name"` imprime un dato del usuario logueado (su username). Vienen de una dependencia nueva en el `pom.xml`: `thymeleaf-extras-springsecurity6`.

**Importante:** esto es solo cosmético. Ocultar un botón con `sec:authorize` no impide que alguien llame la URL directamente (por ejemplo, con Postman). La protección real siempre está en el backend — hoy en `SecurityConfig`, y desde la próxima clase también en `@PreAuthorize`.

---

## 7. Repaso rápido — dudas frecuentes

| Duda | Respuesta |
|---|---|
| ¿Por qué cualquier usuario logueado puede editar/eliminar cursos, no solo el admin? | Todavía no llegamos a esa parte — es el contenido de S11. Hoy solo resolvemos el login. |
| ¿Puedo ver la contraseña real de un usuario mirando la tabla `usuarios`? | No. El hash BCrypt no se puede "deshacer" — ni vos, ni el profesor, ni nadie que robe la base de datos puede recuperar la contraseña original. |
| ¿Por qué la misma contraseña (`admin123`) da un hash distinto en cada usuario? | Por el salt aleatorio que BCrypt agrega en cada hash. Es intencional — evita que se note si dos usuarios comparten contraseña. |
| ¿Por qué el logout es un botón y no un link? | Porque un link es un `GET`, y Spring Security exige un `POST` con token CSRF para cualquier operación que cambie estado (como cerrar sesión). |
| ¿Necesito escribir un `LoginController`? | No. `formLogin()` en `SecurityConfig` ya intercepta el `POST /login` automáticamente. Vos solo hacés la vista (`login.html`). |
| ¿Qué significa `ROLE_` en el rol? | Spring Security lo agrega solo cuando usás `.roles("ADMIN")` — vos guardás `"ADMIN"` en la base de datos, sin el prefijo. |

---

## Para seguir leyendo

| Tema | Enlace |
|---|---|
| Spring Security — Reference Documentation | https://docs.spring.io/spring-security/reference/index.html |
| Spring Security — Username/Password Authentication | https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html |
| Spring Security — Password Storage | https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html |
| Baeldung — CSRF Protection with Spring MVC and Thymeleaf | https://www.baeldung.com/csrf-thymeleaf-with-spring-security |
| Baeldung — Spring Security Login Form | https://www.baeldung.com/spring-security-login |
| thymeleaf-extras-springsecurity — GitHub | https://github.com/thymeleaf/thymeleaf-extras-springsecurity |
