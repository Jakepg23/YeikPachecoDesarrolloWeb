package com.ufide.eventapp.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Entidad Evento - representa un evento publico (concierto, taller, charla...).
 *
 * NOTA IMPORTANTE para el Caso Practico 1:
 *   Esta entidad tiene los campos pero NO tiene validaciones.
 *   Como parte del examen tenes que aplicar las anotaciones de
 *   Bean Validation que veas necesarias (@NotBlank, @Size, @Future, etc).
 *
 *   Tampoco hay metodos util tipo isLleno() o isProximo() - si te sirven
 *   para la vista, podes agregarlos.
 */

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    @Column(nullable = false, length = 120)
    private String nombre;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha debe ser actual o futura")
    @Column(nullable = false)
    private LocalDate fecha;

    @NotBlank(message = "El lugar es obligatorio")
    @Size(max = 100, message = "El lugar no puede superar los 100 caracteres")
    @Column(length = 100)
    private String lugar;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede superar los 50 caracteres")
    @Column(length = 50)
    private String categoria;

    @NotBlank(message = "El organizador es obligatorio")
    @Size(max = 80, message = "El organizador no puede superar los 80 caracteres")
    @Column(length = 80)
    private String organizador;

    @Positive(message = "El cupo máximo debe ser mayor que 0")
    private int cupoMaximo;

    @Min(value = 0, message = "Los cupos vendidos no pueden ser negativos")
    private int cuposVendidos;

    @PositiveOrZero(message = "El precio no puede ser negativo")
    private double precio;

    public Evento() {}

    public Evento(String nombre, String descripcion, LocalDate fecha, String lugar,
                  String categoria, String organizador,
                  int cupoMaximo, int cuposVendidos, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.lugar = lugar;
        this.categoria = categoria;
        this.organizador = organizador;
        this.cupoMaximo = cupoMaximo;
        this.cuposVendidos = cuposVendidos;
        this.precio = precio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getOrganizador() { return organizador; }
    public void setOrganizador(String organizador) { this.organizador = organizador; }

    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public int getCuposVendidos() { return cuposVendidos; }
    public void setCuposVendidos(int cuposVendidos) { this.cuposVendidos = cuposVendidos; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}