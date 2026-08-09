package com.ufide.biblioapp.service;

import com.ufide.biblioapp.entity.Prestamo;
import com.ufide.biblioapp.entity.Usuario;
import com.ufide.biblioapp.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroService libroService;

    public List<Prestamo> listar() {
        return prestamoRepository.findAll();
    }

    public Optional<Prestamo> buscarPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    public List<Prestamo> listarPorUsuario(Usuario usuario) {
        return prestamoRepository.findByUsuario(usuario);
    }

    @Transactional
    public Prestamo registrar(Prestamo prestamo) {
        if (prestamo.getLibro() == null) {
            throw new IllegalArgumentException("Debe seleccionar un libro");
        }

        if (prestamo.getUsuario() == null) {
            throw new IllegalArgumentException("Debe seleccionar un usuario");
        }

        LocalDate fechaPrestamo = LocalDate.now();

        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaLimite(fechaPrestamo.plusDays(14));
        prestamo.setFechaDevolucion(null);

        libroService.descontarCopia(prestamo.getLibro());

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo devolver(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontro el prestamo con id: " + id
                ));

        if (prestamo.getFechaDevolucion() != null) {
            throw new IllegalStateException("El prestamo ya fue devuelto");
        }

        prestamo.setFechaDevolucion(LocalDate.now());

        libroService.devolverCopia(prestamo.getLibro());

        return prestamoRepository.save(prestamo);
    }
}