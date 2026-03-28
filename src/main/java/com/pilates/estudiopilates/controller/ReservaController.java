package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.dto.ReservaRequest;
import com.pilates.estudiopilates.model.Reserva;
import com.pilates.estudiopilates.service.ReservaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    // Servicio que contiene la lógica de negocio de las reservas
    private final ReservaService reservaService;

    // Constructor con inyección de dependencias
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Devuelve la lista completa de reservas
    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.obtenerTodas();
    }

    // Devuelve una reserva concreta buscando por su id
    @GetMapping("/{id}")
    public Optional<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        return reservaService.obtenerPorId(id);
    }

    // Crea una nueva reserva recibiendo únicamente
    // el id del usuario y el id de la sesión
    @PostMapping
    public Reserva crearReserva(@RequestBody ReservaRequest request) {

        // Llamamos al service pasando los ids necesarios
        return reservaService.guardar(request.getUsuarioId(), request.getSesionId());
    }

    // Elimina una reserva por su id
    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
    }
}