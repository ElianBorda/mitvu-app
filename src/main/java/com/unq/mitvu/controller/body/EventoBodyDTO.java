package com.unq.mitvu.controller.body;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventoBodyDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    private String descripcion;
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;
    private String idComision;
    private boolean generarParaTodos = false;
}
