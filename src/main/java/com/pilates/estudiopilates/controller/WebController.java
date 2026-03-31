package com.pilates.estudiopilates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Controller para páginas HTML
 */
@Controller
public class WebController {

    /*
     * Página Conócenos
     */
    @GetMapping("/conocenos")
    public String conocenos() {
        return "conocenos";
    }

    /*
     * Página web de reservas/sesiones
     */
    @GetMapping("/sesiones-web")
    public String sesionesWeb() {
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
}