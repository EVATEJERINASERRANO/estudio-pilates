package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Sesion;
import com.pilates.estudiopilates.service.SesionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        List<Sesion> sesiones = sesionService.obtenerTodas();

        Map<LocalDate, List<Sesion>> sesionesPorDia = sesiones.stream()
                .sorted(Comparator.comparing(Sesion::getFecha)
                        .thenComparing(Sesion::getHora))
                .collect(Collectors.groupingBy(
                        Sesion::getFecha,
                        TreeMap::new,
                        Collectors.toList()
                ));

        model.addAttribute("sesionesPorDia", sesionesPorDia);

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