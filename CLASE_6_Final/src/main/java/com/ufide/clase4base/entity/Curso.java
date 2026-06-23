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

/**
 * Entidad Curso - VERSION FINAL CLASE 5 con validaciones y campo imagenUrl.
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

    @NotBlank(message = "El profesor es obligatorio")
    private String profesor;

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

    public String getProfesor() { return profesor; }
    public void setProfesor(String profesor) { this.profesor = profesor; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}
