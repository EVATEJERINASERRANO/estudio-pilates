package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Clase;
import com.pilates.estudiopilates.model.Sesion;
import com.pilates.estudiopilates.repository.ClaseRepository;
import com.pilates.estudiopilates.repository.ReservaService;
import com.pilates.estudiopilates.service.SesionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin/sesiones")
public class AdminSesionController {

    private final SesionService sesionService;
    private final ClaseRepository claseRepository;
    private final ReservaService reservaService;

    public AdminSesionController(SesionService sesionService,
                                 ClaseRepository claseRepository,
                                 ReservaService reservaService) {
        this.sesionService = sesionService;
        this.claseRepository = claseRepository;
        this.reservaService = reservaService;
    }

    @GetMapping("/nueva")
    public String nuevaSesion(Model model) {
        List<Clase> clases = claseRepository.findAll();

        model.addAttribute("modo", "crear");
        model.addAttribute("clases", clases);
        model.addAttribute("sesion", new Sesion());

        return "admin-sesion-form";
    }

    @GetMapping("/editar/{id}")
    public String editarSesion(@PathVariable Long id, Model model) {
        Sesion sesion = sesionService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Sesión no encontrada"));

        List<Clase> clases = claseRepository.findAll();

        model.addAttribute("modo", "editar");
        model.addAttribute("clases", clases);
        model.addAttribute("sesion", sesion);

        return "admin-sesion-form";
    }

    @PostMapping("/guardar")
    public String guardarSesion(@RequestParam(required = false) Long id,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
                                @RequestParam Integer plazasDisponibles,
                                @RequestParam Long claseId,
                                RedirectAttributes redirectAttributes) {

        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));

        Sesion sesion;

        if (id != null) {
            sesion = sesionService.obtenerPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Sesión no encontrada"));
        } else {
            sesion = new Sesion();
        }

        sesion.setFecha(fecha);
        sesion.setHora(hora);
        sesion.setPlazasDisponibles(plazasDisponibles);
        sesion.setClase(clase);

        if (sesion.getCancelada() == null) {
            sesion.setCancelada(false);
        }

        sesionService.guardar(sesion);

        if (id != null) {
            redirectAttributes.addFlashAttribute("mensajeOk", "Sesión actualizada correctamente");
        } else {
            redirectAttributes.addFlashAttribute("mensajeOk", "Sesión creada correctamente");
        }

        return "redirect:/admin/sesiones";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarOCancelarSesion(@PathVariable Long id,
                                          RedirectAttributes redirectAttributes) {

        Sesion sesion = sesionService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Sesión no encontrada"));

        boolean tieneReservas = reservaService.existenReservasParaSesion(id);

        if (tieneReservas) {
            sesion.setCancelada(true);
            sesionService.guardar(sesion);
            redirectAttributes.addFlashAttribute("mensajeOk", "La sesión tenía reservas y ha sido cancelada");
        } else {
            sesionService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeOk", "Sesión eliminada correctamente");
        }

        return "redirect:/admin/sesiones";
    }
}