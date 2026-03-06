package com.pilates.estudiopilates.repository;

import com.pilates.estudiopilates.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
}