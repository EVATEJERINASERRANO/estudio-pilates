package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Sesion;
import com.pilates.estudiopilates.service.SesionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

// Este controller sirve para mostrar páginas HTML (Thymeleaf)
@Controller
public class SesionViewController {

    private final SesionService sesionService;

    // Constructor con inyección de dependencias
    public SesionViewController(SesionService sesionService) {
        this.sesionService = sesionService;
    }

    // Muestra la página de sesiones
    @GetMapping("/web/sesiones")
    public String verSesiones(Model model) {

        // Obtenemos todas las sesiones desde el service
        List<Sesion> sesiones = sesionService.obtenerTodas();

        // Enviamos la lista al HTML
        model.addAttribute("sesiones", sesiones);

        // Devuelve el nombre del archivo HTML (sin .html)
        return "sesiones";
    }
}