package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Sesion;
import com.pilates.estudiopilates.service.SesionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sesiones")
public class SesionController {

    private final SesionService sesionService;

    public SesionController(SesionService sesionService) {
        this.sesionService = sesionService;
    }

    @GetMapping
    public List<Sesion> listarSesiones() {
        return sesionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<Sesion> obtenerSesionPorId(@PathVariable Long id) {
        return sesionService.obtenerPorId(id);
    }

    @PostMapping
    public Sesion crearSesion(@RequestBody Sesion sesion) {
        return sesionService.guardar(sesion);
    }

    @DeleteMapping("/{id}")
    public void eliminarSesion(@PathVariable Long id) {
        sesionService.eliminar(id);
    }
}