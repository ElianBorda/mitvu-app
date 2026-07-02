package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoMetrica {
    BAJA("Baja"),
    ASISTENCIA("Asistencia");

    private final String descripcionTipoMetrica;
}
