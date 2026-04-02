package com.pilates.estudiopilates.service;

import com.pilates.estudiopilates.model.Reserva;
import com.pilates.estudiopilates.model.Sesion;
import com.pilates.estudiopilates.model.Usuario;
import com.pilates.estudiopilates.repository.ReservaRepository;
import com.pilates.estudiopilates.repository.SesionRepository;
import com.pilates.estudiopilates.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SesionRepository sesionRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            SesionRepository sesionRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.reservaRepository = reservaRepository;
        this.sesionRepository = sesionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva guardar(Long usuarioId, Long sesionId) {

        if (reservaRepository.existsByUsuarioIdAndSesionId(usuarioId, sesionId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El usuario ya tiene una reserva para esta sesión"
            );
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sesión no encontrada"
                ));

        if (sesion.getFecha().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede reservar una sesión pasada"
            );
        }

        if (sesion.getPlazasDisponibles() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No quedan plazas disponibles para esta sesión"
            );
        }

        sesion.setPlazasDisponibles(sesion.getPlazasDisponibles() - 1);
        sesionRepository.save(sesion);

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setSesion(sesion);
        reserva.setFechaReserva(LocalDate.now());

        return reservaRepository.save(reserva);
    }

    public void eliminar(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reserva no encontrada"
                ));

        Sesion sesion = reserva.getSesion();
        sesion.setPlazasDisponibles(sesion.getPlazasDisponibles() + 1);

        sesionRepository.save(sesion);
        reservaRepository.delete(reserva);
    }

    public Reserva guardarDesdeEmail(String email, Long sesionId) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        return guardar(usuario.getId(), sesionId);
    }

    public List<Reserva> obtenerReservasPorEmail(String email) {
        return reservaRepository.findByUsuarioEmailOrderBySesionFechaAscSesionHoraAsc(email);
    }

    public Set<Long> obtenerIdsSesionesReservadasPorEmail(String email) {
        return reservaRepository.findByUsuarioEmailOrderBySesionFechaAscSesionHoraAsc(email)
                .stream()
                .map(reserva -> reserva.getSesion().getId())
                .collect(Collectors.toSet());
    }
}