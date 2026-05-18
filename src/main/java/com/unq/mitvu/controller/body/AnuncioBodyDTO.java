package com.unq.mitvu.controller.body;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnuncioBodyDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    private String descripcion;
    private String idComision;
    private String creadoPorId;

}
