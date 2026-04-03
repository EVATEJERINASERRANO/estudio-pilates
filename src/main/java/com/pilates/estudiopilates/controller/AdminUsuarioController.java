package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Usuario;
import com.pilates.estudiopilates.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final UsuarioRepository usuarioRepository;

    public AdminUsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin-usuario-form";
    }

    @PostMapping
    public String guardarUsuario(@ModelAttribute Usuario usuario,
                                 RedirectAttributes redirectAttributes) {

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ya existe un usuario con ese email");
            return "redirect:/admin/usuarios/nuevo";
        }

        usuario.setRol("CLIENTE");
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("mensajeOk", "Cliente creado correctamente");
        return "redirect:/admin";
    }
}