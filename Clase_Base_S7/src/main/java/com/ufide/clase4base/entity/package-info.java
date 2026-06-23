// CLASE 7 - PARTE A: archivo a CREAR durante la clase.
//
// Crea un nuevo archivo Profesor.java en este mismo paquete (entity/) y copia el
// contenido del bloque de abajo. Es la entidad que vamos a relacionar con Curso.
//
// Pasos:
//   1) Click derecho en este paquete (entity) > New File > Profesor.java
//   2) Copiar el codigo de adentro del comentario /* ... */
//   3) Guardar y verificar que la app arranca - Hibernate creara la tabla profesores.
//
/*
package com.ufide.clase4base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(nullable = false)
    private String nombre;

    @Email(message = "Email invalido")
    @Size(max = 120)
    private String email;

    @Size(max = 80)
    private String especialidad;

    // CLASE 7 - PARTE D (BONUS): descomentar la relacion inversa 1:N.
    // Con esto, profesor.getCursos() devuelve la lista de cursos que dicta.
    // mappedBy = "profesor" apunta al campo de Curso (no a la columna).
    // El cascade = ALL hace que al borrar un Profesor se borren sus Cursos.
    //
    // @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private List<Curso> cursos = new ArrayList<>();

    public Profesor() { }

    public Profesor(String nombre, String email, String especialidad) {
        this.nombre = nombre;
        this.email = email;
        this.especialidad = especialidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    // CLASE 7 - PARTE D (BONUS):
    // public List<Curso> getCursos() { return cursos; }
    // public void setCursos(List<Curso> cursos) { this.cursos = cursos; }
}
*/
