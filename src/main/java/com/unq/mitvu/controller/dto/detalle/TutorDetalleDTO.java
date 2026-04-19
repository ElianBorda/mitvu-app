package com.unq.mitvu.controller.dto.detalle;

import com.unq.mitvu.controller.dto.resumen.TutorResumenDTO;
import lombok.Data;

import java.util.List;

@Data
public class TutorDetalleDTO extends TutorResumenDTO {
    private List<ComisionParaTutorDTO> comisiones;
}