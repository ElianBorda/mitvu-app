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
    private Comision comision;
    private int cantidadAsistencias;

    public Estudiante(int cantidadAsistencias, String carrera, String dni, String nombre, String apellido) {
        this.cantidadAsistencias = cantidadAsistencias;
        this.carrera = carrera;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Estudiante(String apellido, String nombre, String dni, String carrera, Comision comision, Integer cantidadAsistencias) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.carrera = carrera;
        this.comision = comision;
        this.cantidadAsistencias = cantidadAsistencias == null ? 0 : cantidadAsistencias;
    }
}
