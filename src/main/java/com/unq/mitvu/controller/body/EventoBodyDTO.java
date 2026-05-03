package com.unq.mitvu.controller.body;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoBodyDTO {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    private String descripcion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    private String idComision;
    private boolean generarParaTodos = false;
}
