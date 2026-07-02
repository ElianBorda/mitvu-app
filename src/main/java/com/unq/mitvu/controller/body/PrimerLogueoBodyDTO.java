package com.unq.mitvu.controller.body;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrimerLogueoBodyDTO {
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;
    @NotBlank(message = "La contraseña temporal es obligatoria")
    private String passwordTemporal;
    @NotBlank(message = "La nueva contraseña es obligatoria")
    private String passwordNueva;
}