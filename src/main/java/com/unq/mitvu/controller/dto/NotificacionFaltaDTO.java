package com.unq.mitvu.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionFaltaDTO {
    public String idEstudiante;
    public String correoDestino;
    public String nombreEstudiante;
    public Integer cantidadDeFaltas;
}
