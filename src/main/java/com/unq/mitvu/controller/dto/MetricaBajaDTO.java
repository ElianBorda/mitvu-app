package com.unq.mitvu.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetricaBajaDTO {
    public Integer cantidadDeEstudiantesDadoDeBaja;
    public Integer cantidadTotalDeEstudiantes;
    public Integer cantidadDeEstudiantesActivos;
}
