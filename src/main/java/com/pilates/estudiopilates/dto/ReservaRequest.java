package com.pilates.estudiopilates.dto;

public class ReservaRequest {

    // Solo necesitamos la sesión
    private Long sesionId;

    public ReservaRequest() {
    }

    public Long getSesionId() {
        return sesionId;
    }

    public void setSesionId(Long sesionId) {
        this.sesionId = sesionId;
    }
}