package com.ufide.biblioapp.controller;

import com.ufide.biblioapp.entity.Libro;
import com.ufide.biblioapp.entity.Prestamo;
import com.ufide.biblioapp.entity.Usuario;
import com.ufide.biblioapp.service.LibroService;
import com.ufide.biblioapp.service.PrestamoService;
import com.ufide.biblioapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private LibroService libroService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/prestamos")
    public String listar(Model model) {
        model.addAttribute("prestamos", prestamoService.listar());
        return "prestamos";
    }

    @GetMapping("/prestamos/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libros", libroService.listar());
        model.addAttribute("usuarios", usuarioService.listar());
        return "prestamo-form";
    }

    @PostMapping("/prestamos")
    public String registrar(
            @RequestParam Long libroId,
            @RequestParam Long usuarioId) {

        Libro libro = libroService.buscarPorId(libroId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontro el libro con id: " + libroId
                ));

        Usuario usuario = usuarioService.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontro el usuario con id: " + usuarioId
                ));

        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);

        prestamoService.registrar(prestamo);

        return "redirect:/prestamos";
    }

    @PostMapping("/prestamos/{id}/devolver")
    public String devolver(@PathVariable Long id) {
        prestamoService.devolver(id);
        return "redirect:/prestamos";
    }
}