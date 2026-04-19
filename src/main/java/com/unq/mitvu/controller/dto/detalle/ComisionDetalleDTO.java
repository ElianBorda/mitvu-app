package com.unq.mitvu.controller.dto.detalle;

import com.unq.mitvu.controller.dto.resumen.ComisionResumenDTO;
import com.unq.mitvu.controller.dto.resumen.EstudianteResumenDTO;
import com.unq.mitvu.controller.dto.resumen.TutorResumenDTO;
import lombok.Data;

import java.util.List;

@Data
public class ComisionDetalleDTO extends ComisionResumenDTO {
    private TutorResumenDTO tutor;
    private List<EstudianteResumenDTO> estudiantes;
}