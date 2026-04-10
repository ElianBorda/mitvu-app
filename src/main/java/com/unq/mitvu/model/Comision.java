package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
    private String horario;
    private Turno turno;
    private Tutor tutor;
    private String mailTutor;
    private List<String> alumnosDni = new ArrayList<>();

    public Comision(String localidad, String departamento, String carrera, String aula, String horario, Tutor tutor, String mailTutor) {
        this.localidad = localidad;
        this.departamento = departamento;
        this.carrera = carrera;
        this.aula = aula;
        this.horario = horario;
        this.tutor = tutor;
        this.mailTutor = mailTutor;
    }
}
