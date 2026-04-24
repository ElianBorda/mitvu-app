package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private DiaHabil diaHabil;
    private Turno turno;

    @DocumentReference(lazy = true)
    private Tutor tutor;

    public Comision(String localidad, String departamento, String carrera, String aula, Horario horarioInicio, Horario horarioFin, DiaHabil diaHabil) {
        this.localidad = localidad;
        this.departamento = departamento;
        this.carrera = carrera;
        this.aula = aula;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.diaHabil = diaHabil;
        this.turno = horarioInicio.definirTurnoConHorarioFinal(horarioFin);
    }
}
