package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.MensajeContacto;
import com.pilates.estudiopilates.repository.MensajeContactoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminMensajeController {

    private final MensajeContactoRepository mensajeContactoRepository;

    public AdminMensajeController(MensajeContactoRepository mensajeContactoRepository) {
        this.mensajeContactoRepository = mensajeContactoRepository;
    }

    @GetMapping("/admin/mensajes")
    public String verMensajes(Model model) {

        var mensajes = mensajeContactoRepository.findAll();

        var noLeidos = mensajes.stream()
                .filter(m -> !m.isLeido())
                .toList();

        var leidos = mensajes.stream()
                .filter(MensajeContacto::isLeido)
                .toList();

        model.addAttribute("noLeidos", noLeidos);
        model.addAttribute("leidos", leidos);

        return "admin-mensajes";
    }

    @PostMapping("/admin/mensajes/marcar-leido")
    public String marcarLeido(@RequestParam Long id) {

        var mensaje = mensajeContactoRepository.findById(id).orElse(null);

        if (mensaje != null) {
            mensaje.setLeido(true);
            mensajeContactoRepository.save(mensaje);
        }

        return "redirect:/admin/mensajes";
    }
}