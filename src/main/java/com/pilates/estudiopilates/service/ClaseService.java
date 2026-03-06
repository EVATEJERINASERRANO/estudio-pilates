package com.pilates.estudiopilates.service;

import com.pilates.estudiopilates.model.Clase;
import com.pilates.estudiopilates.repository.ClaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    private final ClaseRepository claseRepository;

    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    public List<Clase> obtenerTodas() {
        return claseRepository.findAll();
    }

    public Optional<Clase> obtenerPorId(Long id) {
        return claseRepository.findById(id);
    }

    public Clase guardar(Clase clase) {
        return claseRepository.save(clase);
    }

    public void eliminar(Long id) {
        claseRepository.deleteById(id);
    }
}