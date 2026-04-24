package com.unq.mitvu.controller.dto;

import lombok.Data;

@Data
public class AsistenciaDTO {
    // Usamos String o LocalDate dependiendo de cómo lo maneje tu frontend
    private String fecha;
    private boolean asistio;
    private String observacion;
}