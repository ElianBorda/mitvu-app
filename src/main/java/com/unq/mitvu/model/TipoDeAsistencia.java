package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDeAsistencia {
    PRESENTE("Presente"),
    AUSENTE("Ausente"),
    AUSENCIA_JUSTIFICADA("Ausencia justificada");

    private final String descripcionTipoDeAsistencia;
}
