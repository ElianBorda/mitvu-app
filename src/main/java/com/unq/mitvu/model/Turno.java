package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Turno {
    MANANA("Mañana"),
    TARDE("Tarde"),
    NOCHE("Noche");

    private final String descripcionTurno;
}
