package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Reserva;
import com.pilates.estudiopilates.model.Sesion;
import com.pilates.estudiopilates.repository.MaterialRepository;
import com.pilates.estudiopilates.repository.ReservaService;
import com.pilates.estudiopilates.service.SesionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WebController {

    private final SesionService sesionService;
    private final ReservaService reservaService;
    private final MaterialRepository materialRepository;

    public WebController(SesionService sesionService,
                         ReservaService reservaService,
                         MaterialRepository materialRepository) {
        this.sesionService = sesionService;
        this.reservaService = reservaService;
        this.materialRepository = materialRepository;
    }

    @GetMapping("/conocenos")
    public String conocenos() {
        return "conocenos";
    }

    @GetMapping("/sesiones-web")
    public String sesionesWeb(Model model, Principal principal) {

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

        if (principal != null) {
            String email = principal.getName();

            List<Reserva> misReservas = reservaService.obtenerReservasPorEmail(email);
            Set<Long> sesionesReservadasIds = reservaService.obtenerIdsSesionesReservadasPorEmail(email);

            model.addAttribute("misReservas", misReservas);
            model.addAttribute("sesionesReservadasIds", sesionesReservadasIds);
        } else {
            model.addAttribute("misReservas", Collections.emptyList());
            model.addAttribute("sesionesReservadasIds", Collections.emptySet());
        }

        return "sesiones";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/sesiones")
    public String adminSesiones(Model model) {

        List<Sesion> sesiones = sesionService.obtenerTodas();
        List<Reserva> reservas = reservaService.obtenerTodas();

        Map<LocalDate, List<Sesion>> sesionesPorDia = sesiones.stream()
                .sorted(Comparator.comparing(Sesion::getFecha)
                        .thenComparing(Sesion::getHora))
                .collect(Collectors.groupingBy(
                        Sesion::getFecha,
                        TreeMap::new,
                        Collectors.toList()
                ));

        Map<Long, List<Reserva>> reservasPorSesion = reservas.stream()
                .filter(reserva -> reserva.getSesion() != null)
                .collect(Collectors.groupingBy(reserva -> reserva.getSesion().getId()));

        model.addAttribute("sesionesPorDia", sesionesPorDia);
        model.addAttribute("reservasPorSesion", reservasPorSesion);

        return "admin-sesiones";
    }

    @GetMapping("/admin/reservas")
    public String adminReservas(Model model) {
        model.addAttribute("reservas", reservaService.obtenerTodas());
        return "admin-reservas";
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

    // 🔥 NUEVO
    @GetMapping("/materiales")
    public String materiales(Model model) {
        model.addAttribute("materiales", materialRepository.findAll());
        return "materiales";
    }
}