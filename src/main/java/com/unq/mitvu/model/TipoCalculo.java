package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoCalculo {
    CANTIDAD("Cantidad absoluta"),
    PORCENTAJE("Porcentaje sobre el total");

    private final String descripcionTipoCalculo;
}