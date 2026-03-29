package com.pilates.estudiopilates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Este controller muestra la página principal de inicio
@Controller
public class HomeController {

    // Al entrar en la raíz de la web, se carga index.html
    @GetMapping("/")
    public String mostrarInicio() {
        return "index";
    }
}