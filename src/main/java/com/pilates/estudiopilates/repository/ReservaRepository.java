package com.pilates.estudiopilates.repository;

import com.pilates.estudiopilates.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByUsuarioIdAndSesionId(Long usuarioId, Long sesionId);

    List<Reserva> findByUsuarioEmailOrderBySesionFechaAscSesionHoraAsc(String email);

    boolean existsBySesionId(Long sesionId);
}