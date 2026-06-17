package com.unq.mitvu.controller.body;

import com.unq.mitvu.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdministradorBodyDTO {

    @NotBlank(message = "El apellido es obligatorio")
    protected String apellido;

    @NotBlank(message = "El nombre es obligatorio")
    protected String nombre;

    @NotBlank(message = "El DNI es obligatorio")
    protected String dni;

    @NotBlank(message = "El mail es obligatorio")
    @Email(message = "Debe ser un formato de correo electrónico válido")
    protected String mail;

}
