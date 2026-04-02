package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.repository.ReservaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Este controller gestiona las reservas hechas desde formularios HTML
@Controller
public class ReservaViewController {

    private final ReservaService reservaService;

    // Constructor con inyección de dependencias
    public ReservaViewController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Recibe los datos del formulario y crea la reserva
    @PostMapping("/reservas/form")
    public String crearReservaDesdeFormulario(
            @RequestParam Long usuarioId,
            @RequestParam Long sesionId) {

        // Guardamos la reserva usando la lógica del service
        reservaService.guardar(usuarioId, sesionId);

        // Redirigimos de nuevo a la vista de sesiones
        return "redirect:/web/sesiones";
    }
}