package com.unq.mitvu.controller.dto.detalle;

import com.unq.mitvu.controller.dto.resumen.EstudianteResumenDTO;
import lombok.Data;

@Data
public class EstudianteDetalleDTO extends EstudianteResumenDTO {
    private ComisionParaEstudianteDTO comision;
}