package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rol {
    ADMIN("Admin"),
    TUTOR("Tutor"),
    ESTUDIANTE("Estudiante");

    private final String descripcionRol;
}
