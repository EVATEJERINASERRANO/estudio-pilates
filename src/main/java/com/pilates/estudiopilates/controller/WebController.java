package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.service.SesionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final SesionService sesionService;

    public WebController(SesionService sesionService) {
        this.sesionService = sesionService;
    }

    @GetMapping("/conocenos")
    public String conocenos() {
        return "conocenos";
    }

    @GetMapping("/sesiones-web")
    public String sesionesWeb(Model model) {
        model.addAttribute("sesiones", sesionService.obtenerTodas());
        return "sesiones";
    }

    @GetMapping("/experiencias")
    public String experiencias() {
        return "experiencias";
    }

    @GetMapping("/boutique")
    public String boutique() {
        return "boutique";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}