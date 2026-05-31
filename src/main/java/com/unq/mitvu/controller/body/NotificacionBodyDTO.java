package com.unq.mitvu.controller.body;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacionBodyDTO {
    private String idUsuario;
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    @NotBlank(message = "El cuerpo es obligatorio")
    private String cuerpo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime fecha;
    private boolean leida;
}
