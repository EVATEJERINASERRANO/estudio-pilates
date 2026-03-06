package com.pilates.estudiopilates.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "sesiones")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private LocalTime hora;

    private Integer plazasDisponibles;

    @ManyToOne
    @JoinColumn(name = "clase_id")
    private Clase clase;

    public Sesion() {
    }
//getters y setters
    public Sesion(LocalDate fecha, LocalTime hora, int plazasDisponibles, Clase clase) {
        this.fecha = fecha;
        this.hora = hora;
        this.plazasDisponibles = plazasDisponibles;
        this.clase = clase;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public void setPlazasDisponibles(int plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }
}