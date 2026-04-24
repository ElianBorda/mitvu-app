package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "estudiantes")
public class Estudiante extends Usuario {

    private String carrera;
    private List<Asistencia> asistencias = new ArrayList<>();
    private FormularioBaja baja;

    @DocumentReference(lazy = true)
    private Comision comision;

    public Estudiante(String apellido, String nombre, String dni, String mail, String password, Rol rol, String carrera) {
        super(apellido, nombre, dni, mail, password, rol);
        this.carrera = carrera;
    }

    public int getCantidadDeAsistencias() {
        return (int) asistencias.stream().filter(Asistencia::getAsistio).count();
    }

    public int getCantidadDeFaltas(){
        return (int) asistencias.stream().filter(asistencia -> !asistencia.getAsistio()).count();
    }

    public void agregarAsistencia(Asistencia asistencia) {
        asistencias.add(asistencia);
    }
    public boolean estaActivo(){
        return this.baja == null;
    }

}
