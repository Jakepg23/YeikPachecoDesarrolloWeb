package com.ufide.clase4base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// CLASE 7 - PARTE A.1: descomentar cuando agreguemos la relacion @ManyToOne con Profesor
// import jakarta.persistence.FetchType;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;

/**
 * Entidad Curso - estado al inicio de Clase 7.
 *
 * Trae: validaciones, imagenUrl y un String profesor (texto plano).
 *
 * CLASE 7 (Asociaciones Hibernate):
 *   - PARTE A.1: reemplazar el "String profesor" por una relacion @ManyToOne con
 *     la entidad Profesor (que vamos a crear durante la clase).
 *   - PARTE A.2: ajustar getter/setter del nuevo campo.
 *   - PARTE B: en CursoController, cargar la lista de profesores para el form.
 *   - PARTE C: en cursos.html, mostrar profesor.nombre en cada card.
 */
@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Maximo 100 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 500)
    @Column(length = 500)
    private String descripcion;

    @Min(value = 1, message = "Minimo 1 credito")
    @Max(value = 8, message = "Maximo 8 creditos")
    private int creditos;

    // CLASE 7 - PARTE A.1: este campo "String profesor" se reemplaza por la
    // relacion @ManyToOne. Cuando descomentes el bloque de abajo, BORRA esta linea
    // y su getter/setter al final del archivo.
    @NotBlank(message = "El profesor es obligatorio")
    private String profesor;

    /*
     * CLASE 7 - PARTE A.1: descomentar este bloque para crear la relacion N:1.
     *
     * Conceptos:
     *  - @ManyToOne: muchos Cursos pertenecen a UN Profesor.
     *  - fetch = LAZY: el profesor NO se carga junto con el curso. Hibernate solo
     *    hace el SELECT a profesores cuando alguien llama a curso.getProfesor().
     *  - @JoinColumn: define la columna FK en la tabla cursos. Si no se pone,
     *    Hibernate inventa un nombre tipo "profesor_id".
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * @JoinColumn(name = "profesor_id")
     * private Profesor profesor;
     */

    /** Bonus: URL de imagen subida a Firebase Storage. Opcional. */
    @Column(length = 500)
    private String imagenUrl;

    public Curso() {
    }

    public Curso(Long id, String nombre, String descripcion, int creditos, String profesor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
        this.profesor = profesor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    // CLASE 7 - PARTE A.1: cuando reemplaces el campo profesor, estos getter/setter
    // tambien cambian. BORRA los de String y descomenta los de Profesor abajo.
    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }

    /*
     * public Profesor getProfesor() { return profesor; }
     * public void setProfesor(Profesor profesor) { this.profesor = profesor; }
     */

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}
