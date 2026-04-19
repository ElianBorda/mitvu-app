package com.unq.mitvu.controller.dto.resumen;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "numero"})
public class ComisionResumenDTO {
    private String id;
    private Integer numero;
    private String localidad;
    private String departamento;
    private String carrera;
    private String horarioInicio;
    private String horarioFin;
    private String diaHabil;
}