package com.pilates.estudiopilates.repository;

import com.pilates.estudiopilates.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByUsuarioIdAndSesionId(Long usuarioId, Long sesionId);
}