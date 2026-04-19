package com.unq.mitvu.controller.dto.resumen;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.unq.mitvu.controller.dto.AsistenciaDTO;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({"id"})
public class EstudianteResumenDTO {
    private String id;
    private String apellido;
    private String nombre;
    private String dni;
    private String mail;
    private String carrera;
    private String rol;
    private List<AsistenciaDTO> asistencias;
}