package com.unq.mitvu.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.unq.mitvu.model.Rol;
import lombok.Data;

@Data
@JsonPropertyOrder({"id"})
public class AdministradorDTO {
    protected String id;
    protected String apellido;
    protected String nombre;
    protected String dni;
    protected String mail;
    protected Rol rol;
}
