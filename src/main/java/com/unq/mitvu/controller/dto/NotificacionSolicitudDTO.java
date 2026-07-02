package com.unq.mitvu.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionSolicitudDTO {
    private String correoDestino;
    private String nombreTutor;
    private boolean aprobado;
    private String passwordTemporal;
}