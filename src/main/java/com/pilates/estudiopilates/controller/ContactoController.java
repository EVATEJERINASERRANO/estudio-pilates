package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.MensajeContacto;
import com.pilates.estudiopilates.repository.MensajeContactoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class ContactoController {

    private final MensajeContactoRepository mensajeContactoRepository;

    public ContactoController(MensajeContactoRepository mensajeContactoRepository) {
        this.mensajeContactoRepository = mensajeContactoRepository;
    }

    @GetMapping("/contacto")
    public String mostrarContacto(Model model) {
        if (!model.containsAttribute("mensajeContacto")) {
            model.addAttribute("mensajeContacto", new MensajeContacto());
        }
        return "contacto";
    }

    @PostMapping("/contacto")
    public String enviarContacto(MensajeContacto mensajeContacto, RedirectAttributes redirectAttributes) {
        mensajeContacto.setFechaEnvio(LocalDateTime.now());
        mensajeContacto.setLeido(false);

        mensajeContactoRepository.save(mensajeContacto);

        redirectAttributes.addFlashAttribute("mensajeOk",
                "Tu mensaje se ha enviado correctamente. Te responderemos pronto.");
        redirectAttributes.addFlashAttribute("mensajeContacto", new MensajeContacto());

        return "redirect:/contacto#formulario-contacto";
    }
}