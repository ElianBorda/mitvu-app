package com.unq.mitvu.controller.body;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class EstudianteBodyDTO {
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotBlank(message = "El mail es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String mail;

    @NotBlank(message = "La carrera es obligatoria")
    private String carrera;

    private String comision_id;
}
