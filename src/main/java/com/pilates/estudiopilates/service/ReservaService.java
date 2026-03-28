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

@Service
public class ReservaService {

    // Repositorio para trabajar con las reservas
    private final ReservaRepository reservaRepository;

    // Repositorio para trabajar con las sesiones
    private final SesionRepository sesionRepository;

    // Repositorio para trabajar con los usuarios
    private final UsuarioRepository usuarioRepository;

    // Constructor con inyección de dependencias
    public ReservaService(
            ReservaRepository reservaRepository,
            SesionRepository sesionRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.reservaRepository = reservaRepository;
        this.sesionRepository = sesionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Devuelve todas las reservas almacenadas en la base de datos
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    // Busca una reserva por su id
    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }

    // Guarda una nueva reserva a partir del id del usuario y del id de la sesión
    public Reserva guardar(Long usuarioId, Long sesionId) {

        // Comprobamos si el usuario ya tiene una reserva para esa sesión
        if (reservaRepository.existsByUsuarioIdAndSesionId(usuarioId, sesionId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El usuario ya tiene una reserva para esta sesión"
            );
        }

        // Buscamos el usuario en la base de datos
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));

        // Buscamos la sesión en la base de datos
        Sesion sesion = sesionRepository.findById(sesionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sesión no encontrada"
                ));
        // Comprobamos que la sesión no sea en el pasado
        if (sesion.getFecha().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede reservar una sesión pasada");
        }

        // Verificamos si todavía quedan plazas disponibles
        if (sesion.getPlazasDisponibles() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No quedan plazas disponibles para esta sesión"
            );
        }

        // Restamos una plaza porque se va a confirmar la reserva
        sesion.setPlazasDisponibles(sesion.getPlazasDisponibles() - 1);

        // Guardamos la sesión con el nuevo número de plazas
        sesionRepository.save(sesion);

        // Creamos una nueva reserva vacía
        Reserva reserva = new Reserva();

        // Asociamos el usuario a la reserva
        reserva.setUsuario(usuario);

        // Asociamos la sesión a la reserva
        reserva.setSesion(sesion);

        // Guardamos la fecha actual en la que se realiza la reserva
        reserva.setFechaReserva(LocalDate.now());

        // Guardamos la reserva en la base de datos y la devolvemos
        return reservaRepository.save(reserva);
    }

    // Elimina una reserva y devuelve la plaza a la sesión correspondiente
    public void eliminar(Long id) {

        // Buscamos la reserva por su id
        // Si no existe, lanzamos un error 404
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reserva no encontrada"
                ));

        // Obtenemos la sesión asociada a la reserva
        Sesion sesion = reserva.getSesion();

        // Devolvemos una plaza porque la reserva se cancela
        sesion.setPlazasDisponibles(sesion.getPlazasDisponibles() + 1);

        // Guardamos la sesión actualizada
        sesionRepository.save(sesion);

        // Eliminamos la reserva
        reservaRepository.delete(reserva);
    }
}