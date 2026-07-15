package com.ufide.clase4base.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ufide.clase4base.entity.Curso;
import com.ufide.clase4base.service.CursoService;
import com.ufide.clase4base.service.FirebaseService;

// CLASE 7 - PARTE B.3: descomentar para inyectar la lista de profesores en el form
// import com.ufide.clase4base.service.ProfesorService;

import jakarta.validation.Valid;

/**
 * Controlador de cursos - VERSION FINAL CLASE 5 con CRUD completo.
 *
 * Endpoints:
 * GET /cursos -> listar todos (READ)
 * GET /cursos/{id} -> ver detalle (READ)
 * GET /cursos/nuevo -> mostrar form vacio (CREATE - paso 1)
 * POST /cursos -> guardar nuevo (CREATE - paso 2)
 * GET /cursos/{id}/editar -> mostrar form con datos (UPDATE - paso 1)
 * POST /cursos/{id} -> actualizar (UPDATE - paso 2)
 * POST /cursos/{id}/eliminar -> borrar (DELETE)
 */
@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private FirebaseService firebaseService;

    // CLASE 7 - PARTE B.3: descomentar para inyectar el servicio de profesores
    // @Autowired
    // private ProfesorService profesorService;

    // ============================================
    // READ
    // ============================================

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("cursos", cursoService.listar());
        return "cursos";
    }

    @GetMapping("/{id}")
    public String detalle(Model modelo, @PathVariable Long id) {
        Curso encontrado = cursoService.buscarPorId(id).orElse(null);
        modelo.addAttribute("curso", encontrado);
        return "curso";
    }

    // ============================================
    // CREATE
    // ============================================

    @GetMapping("/nuevo")
    public String mostrarFormNuevo(Model modelo) {
        modelo.addAttribute("curso", new Curso());
        // CLASE 7 - PARTE B.3: descomentar para mandar la lista de profesores al form
        // modelo.addAttribute("profesores", profesorService.listar());
        return "cursos/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("curso") Curso curso,
            BindingResult result,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            RedirectAttributes ra) throws IOException {
        if (result.hasErrors()) {
            return "cursos/form";
        }
        // Si hay imagen y Firebase esta activo, subirla
        if (imagen != null && !imagen.isEmpty() && firebaseService.isActivo()) {
            String url = firebaseService.subir(imagen);
            curso.setImagenUrl(url);
        }
        cursoService.guardar(curso);
        ra.addFlashAttribute("ok", "Curso guardado correctamente!");
        return "redirect:/cursos";
    }

    // ============================================
    // UPDATE
    // ============================================

    @GetMapping("/{id}/editar")
    public String mostrarFormEditar(@PathVariable Long id, Model modelo) {
        Curso curso = cursoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado: " + id));
        modelo.addAttribute("curso", curso);
        // CLASE 7 - PARTE B.3: descomentar para mandar profesores al form de edicion
        // modelo.addAttribute("profesores", profesorService.listar());
        return "cursos/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
            @Valid @ModelAttribute("curso") Curso curso,
            BindingResult result,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            RedirectAttributes ra) throws IOException {
        if (result.hasErrors()) {
            return "cursos/form";
        }
        curso.setId(id);
        // Si subio una imagen nueva, reemplazarla
        if (imagen != null && !imagen.isEmpty() && firebaseService.isActivo()) {
            String url = firebaseService.subir(imagen);
            curso.setImagenUrl(url);
        } else {
            // Conservar la imagen existente
            cursoService.buscarPorId(id).ifPresent(existing -> curso.setImagenUrl(existing.getImagenUrl()));
        }
        cursoService.guardar(curso);
        ra.addFlashAttribute("ok", "Curso actualizado correctamente!");
        return "redirect:/cursos";
    }

    // ============================================
    // DELETE
    // ============================================

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            cursoService.eliminar(id);
            ra.addFlashAttribute("ok", "Curso eliminado");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/cursos";
    }
}
