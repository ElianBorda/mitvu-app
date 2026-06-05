package com.unq.mitvu.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataPointDTO {
    private String etiqueta; // Lo que va en el eje X (ej: "Cambio de carrera", "Comisión A", "Mayo")
    private Double valor;    // Lo que va en el eje Y (ej: 15, 8.5, 100)
}