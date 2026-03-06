package com.pilates.estudiopilates.service;

import com.pilates.estudiopilates.model.Reserva;
import com.pilates.estudiopilates.model.Sesion;
import com.pilates.estudiopilates.repository.ReservaRepository;
import com.pilates.estudiopilates.repository.SesionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SesionRepository sesionRepository;

    public ReservaService(ReservaRepository reservaRepository, SesionRepository sesionRepository) {
        this.reservaRepository = reservaRepository;
        this.sesionRepository = sesionRepository;
    }

    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva guardar(Reserva reserva) {
        Long usuarioId = reserva.getUsuario().getId();
        Long sesionId = reserva.getSesion().getId();

        if (reservaRepository.existsByUsuarioIdAndSesionId(usuarioId, sesionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya tiene una reserva para esta sesión");
        }

        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sesión no encontrada"));

        if (sesion.getPlazasDisponibles() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No quedan plazas disponibles para esta sesión");
        }

        sesion.setPlazasDisponibles(sesion.getPlazasDisponibles() - 1);
        sesionRepository.save(sesion);

        reserva.setSesion(sesion);

        return reservaRepository.save(reserva);
    }

    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }
}