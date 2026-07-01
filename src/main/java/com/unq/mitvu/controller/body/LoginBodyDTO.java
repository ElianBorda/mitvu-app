package com.unq.mitvu.controller.body;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginBodyDTO {
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}