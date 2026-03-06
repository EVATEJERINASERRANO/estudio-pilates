package com.pilates.estudiopilates.controller;

import com.pilates.estudiopilates.model.Clase;
import com.pilates.estudiopilates.service.ClaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clases")
public class ClaseController {

    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @GetMapping
    public List<Clase> listarClases() {
        return claseService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Optional<Clase> obtenerClasePorId(@PathVariable Long id) {
        return claseService.obtenerPorId(id);
    }

    @PostMapping
    public Clase crearClase(@RequestBody Clase clase) {
        return claseService.guardar(clase);
    }

    @DeleteMapping("/{id}")
    public void eliminarClase(@PathVariable Long id) {
        claseService.eliminar(id);
    }
}