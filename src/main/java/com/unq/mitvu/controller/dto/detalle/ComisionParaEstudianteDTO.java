package com.unq.mitvu.controller.dto.detalle;

import com.unq.mitvu.controller.dto.resumen.ComisionResumenDTO;
import com.unq.mitvu.controller.dto.resumen.TutorResumenDTO;
import lombok.Data;

@Data
public class ComisionParaEstudianteDTO extends ComisionResumenDTO {
    private TutorResumenDTO tutor;
}
