package com.unq.mitvu.controller.dto;

import com.unq.mitvu.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
    private String id;
    private String nombre;
    private String apellido;
    private String dni;
    private Rol rol;
    private Boolean requiereCambioPassword;
}