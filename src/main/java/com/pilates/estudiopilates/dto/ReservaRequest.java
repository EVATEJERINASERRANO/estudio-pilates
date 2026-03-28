package com.pilates.estudiopilates.dto;

// Esta clase se usa para recibir los datos mínimos necesarios
// desde el frontend o Postman para crear una reserva.
// En lugar de enviar un objeto completo (Reserva),
// solo enviamos los IDs necesarios.
public class ReservaRequest {

    // ID del usuario que quiere hacer la reserva
    private Long usuarioId;

    // ID de la sesión que se quiere reservar
    private Long sesionId;

    // Constructor vacío (necesario para Spring)
    public ReservaRequest() {
    }

    // Getter del usuarioId
    public Long getUsuarioId() {
        return usuarioId;
    }

    // Setter del usuarioId
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Getter del sesionId
    public Long getSesionId() {
        return sesionId;
    }

    // Setter del sesionId
    public void setSesionId(Long sesionId) {
        this.sesionId = sesionId;
    }
}