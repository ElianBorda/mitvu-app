package com.unq.mitvu.controller.body;

import com.unq.mitvu.model.MotivoBaja;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BajaEstudianteBodyDTO {
    @NotNull(message = "El motivo es obligatorio")
    private MotivoBaja motivo;
    private String detalle;
}
