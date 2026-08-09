package com.ufide.biblioapp.service;

import com.ufide.biblioapp.entity.Libro;
import com.ufide.biblioapp.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    public Optional<Libro> buscarPorId(Long id) {
        return libroRepository.findById(id);
    }

    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se encontro el libro con id: " + id
            );
        }

        libroRepository.deleteById(id);
    }

    // ==========================================================
    // CASO PRACTICO 2 - REQUISITO 2:
    // Estos metodos permiten descontar una copia disponible cuando
    // se registra un prestamo y devolverla cuando el libro se entrega.
    // ==========================================================
    public void descontarCopia(Libro libro) {
        if (libro.getCopiasDisponibles() <= 0) {
            throw new IllegalStateException(
                    "No hay copias disponibles para este libro"
            );
        }

        libro.setCopiasDisponibles(
                libro.getCopiasDisponibles() - 1
        );

        libroRepository.save(libro);
    }

    public void devolverCopia(Libro libro) {
        if (libro.getCopiasDisponibles() < libro.getCopiasTotales()) {

            libro.setCopiasDisponibles(
                    libro.getCopiasDisponibles() + 1
            );

            libroRepository.save(libro);
        }
    }
}