package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Reserva;
import com.pilates.estudiopilates.service.ReservaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    @ResponseBody
    public List<Reserva> listarReservas() {
        return reservaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerPorId(id);
    }

    @PostMapping
    public String crearReserva(@RequestParam Long sesionId,
                               @AuthenticationPrincipal User user) {

        String email = user.getUsername();

        reservaService.guardarDesdeEmail(email, sesionId);

        return "redirect:/sesiones-web";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
    }
}