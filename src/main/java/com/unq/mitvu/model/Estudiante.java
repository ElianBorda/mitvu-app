package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

    @DBRef
    private Comision comision;

    private Integer cantidadAsistencias;

    public Estudiante(String apellido, String nombre, String dni, String carrera, Integer cantidadAsistencias, Comision comision) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.carrera = carrera;
        this.cantidadAsistencias = cantidadAsistencias == null ? 0 : cantidadAsistencias;
        this.comision = comision;
    }

    public Estudiante (String apellido, String nombre, String dni, String carrera, Integer cantidadAsistencias) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.carrera = carrera;
        this.cantidadAsistencias = cantidadAsistencias;
    }
}
