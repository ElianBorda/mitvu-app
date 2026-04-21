package com.unq.mitvu.controller.body;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ComisionBodyDTO {
    @NotBlank(message = "La localidad es obligatoria")
    private String localidad;

    @NotBlank(message = "El departamento es obligatorio")
    private String departamento;

    @NotBlank(message = "La carrera es obligatoria")
    private String carrera;

    @NotBlank(message = "El aula es obligatoria")
    private String aula;

    @NotBlank(message = "El horario de inicio es obligatorio")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Formato de hora inválido (HH:mm)")
    private String horarioInicio;

    @NotBlank(message = "El horario de fin es obligatorio")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Formato de hora inválido (HH:mm)")
    private String horarioFin;

    //@NotNull(message = "El día hábil es obligatorio")
    //private String diaHabil;
}
