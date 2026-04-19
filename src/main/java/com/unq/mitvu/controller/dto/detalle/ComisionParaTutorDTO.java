package com.unq.mitvu.controller.dto.detalle;

import com.unq.mitvu.controller.dto.resumen.ComisionResumenDTO;
import com.unq.mitvu.controller.dto.resumen.EstudianteResumenDTO;
import lombok.Data;

import java.util.List;

@Data
public class ComisionParaTutorDTO extends ComisionResumenDTO {
    private List<EstudianteResumenDTO> estudiantes;
}
