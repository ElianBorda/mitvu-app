package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Agrupacion {
    COMISION("Comision"),
    MOTIVO("Motivo"),
    FECHA("Fecha"),
    ANIO("Año"),
    LOCALIDAD("Localidad");

    private final String descripcionAgrupacion;
}
