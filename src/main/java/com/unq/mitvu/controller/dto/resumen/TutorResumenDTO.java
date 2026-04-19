package com.unq.mitvu.controller.dto.resumen;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id"})
public class TutorResumenDTO {
    private String id;
    private String apellido;
    private String nombre;
    private String dni;
    private String mail;
    private String rol;
}