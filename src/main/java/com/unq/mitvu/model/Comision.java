package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "comisiones")
public class Comision {

    @Id
    private String id;
    private Integer numero;
    private String localidad;
    private String departamento;
    private String carrera;
    private String aula;
    private Horario horarioInicio;
    private Horario horarioFin;
    private Turno turno;

    @DBRef
    private Tutor tutor;

    @DBRef
    private ArrayList<Estudiante> estudiantes = new ArrayList<>();

    public Comision(
            Tutor tutor,
            Horario horarioFin,
            Horario horarioInicio,
            String aula,
            String carrera,
            String departamento,
            String localidad,
            Integer numero
    ) {
        this.tutor = tutor;
        this.turno = horarioInicio.definirTurnoConHorarioFinal(horarioFin);
        this.horarioFin = horarioFin;
        this.horarioInicio = horarioInicio;
        this.aula = aula;
        this.carrera = carrera;
        this.departamento = departamento;
        this.localidad = localidad;
        this.numero = numero;
    }

    public Comision(
            ArrayList<Estudiante> estudiantes,
            Horario horarioFin,
            Horario horarioInicio,
            String aula,
            String carrera,
            String departamento,
            String localidad,
            Integer numero
    ) {
        this.estudiantes = estudiantes;
        this.turno = horarioInicio.definirTurnoConHorarioFinal(horarioFin);
        this.horarioFin = horarioFin;
        this.horarioInicio = horarioInicio;
        this.aula = aula;
        this.carrera = carrera;
        this.departamento = departamento;
        this.localidad = localidad;
        this.numero = numero;
    }
}
