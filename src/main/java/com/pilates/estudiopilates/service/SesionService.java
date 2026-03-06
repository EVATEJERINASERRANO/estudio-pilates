package com.pilates.estudiopilates.service;

import com.pilates.estudiopilates.model.Sesion;
import com.pilates.estudiopilates.repository.SesionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SesionService {

    private final SesionRepository sesionRepository;

    public SesionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    public List<Sesion> obtenerTodas() {
        return sesionRepository.findAll();
    }

    public Optional<Sesion> obtenerPorId(Long id) {
        return sesionRepository.findById(id);
    }

    public Sesion guardar(Sesion sesion) {
        return sesionRepository.save(sesion);
    }

    public void eliminar(Long id) {
        sesionRepository.deleteById(id);
    }
}