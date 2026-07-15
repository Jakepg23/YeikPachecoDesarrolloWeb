// CLASE 7 - PARTE B: archivo a CREAR durante la clase.
//
// Crea un nuevo archivo ProfesorController.java en este mismo paquete (controller/)
// con el contenido del bloque /* ... */ de abajo. Es un CRUD basico para gestionar
// profesores antes de relacionarlos con cursos.
//
// IMPORTANTE: tambien hay que modificar CursoController para que el form de
// nuevo curso reciba la lista de profesores. Ver instruccion B.3 en el guion.
//
/*
package com.ufide.clase4base.controller;

import com.ufide.clase4base.entity.Profesor;
import com.ufide.clase4base.service.ProfesorService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profesores", service.listar());
        return "profesores";
    }

    @GetMapping("/nuevo")
    public String mostrarFormNuevo(Model model) {
        model.addAttribute("profesor", new Profesor());
        return "profesores/form";
    }

    @PostMapping
    public String guardar(@Valid @ModelAttribute("profesor") Profesor profesor,
                          BindingResult result,
                          RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "profesores/form";
        }
        service.guardar(profesor);
        ra.addFlashAttribute("ok", "Profesor guardado correctamente");
        return "redirect:/profesores";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
        Profesor p = service.buscarPorId(id).orElseThrow();
        model.addAttribute("profesor", p);
        return "profesores/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("profesor") Profesor profesor,
                             BindingResult result,
                             RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "profesores/form";
        }
        profesor.setId(id);
        service.guardar(profesor);
        ra.addFlashAttribute("ok", "Profesor actualizado correctamente");
        return "redirect:/profesores";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.eliminar(id);
            ra.addFlashAttribute("ok", "Profesor eliminado correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error",
                "No se puede eliminar el profesor: tiene cursos asignados");
        }
        return "redirect:/profesores";
    }
}
*/
