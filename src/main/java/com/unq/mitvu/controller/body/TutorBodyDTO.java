package com.unq.mitvu.controller.body;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
public class TutorBodyDTO {
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotBlank(message = "El mail es obligatorio")
    @Email(message = "Debe ser un formato de correo electrónico válido")
    private String mail;

    private List<String> comisiones_ids;
}
