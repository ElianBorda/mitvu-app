package com.unq.mitvu.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetricaAsistenciaDTO {
    public Integer totalEstudiantes;
    public Integer porcentajeAsistencia;
    public Integer porcentajeFalta;
    public Integer porcentajeFaltaJustificada;
    public EventoDTO evento;
}
