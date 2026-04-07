package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "estudiantes")
public class Estudiante {

    @Id
    private String id;

    private String apellido;
    private String nombre;
    private String dni;
    private String carrera;
    private String comision_id;
    private int cantidad_asistencias;

    public Estudiante(String apellido, String nombre, String dni, String carrera, String comisionId) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.carrera = carrera;
        this.comision_id = comisionId;
        this.cantidad_asistencias = 0;
    }

    public Estudiante(String nombre) {
        this.nombre = nombre;
    }
}
