package com.unq.mitvu.body;

import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Tutor;
import com.unq.mitvu.model.Turno;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ComisionBody {

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

    public ComisionBody() {
    }

    public ComisionBody(Integer numero, String localidad, String departamento, String carrera, String aula, String horario, Turno turno, Tutor tutor, String mailTutor, List<String> alumnosDni) {
        this.numero = numero;
        this.localidad = localidad;
        this.departamento = departamento;
        this.carrera = carrera;
        this.aula = aula;
        this.horario = horario;
        this.turno = turno;
        this.tutor = tutor;
        this.mailTutor = mailTutor;
        this.alumnosDni = alumnosDni;
    }

    public static ComisionBody fromComision(Comision comision) {
        return new ComisionBody(
                comision.getNumero(),
                comision.getLocalidad(),
                comision.getDepartamento(),
                comision.getCarrera(),
                comision.getAula(),
                comision.getHorario(),
                comision.getTurno(),
                comision.getTutor(),
                comision.getMailTutor(),
                comision.getAlumnosDni()
        );
    }

    public Comision toComision() {
        Comision comision = new Comision(
                localidad,
                departamento,
                carrera,
                aula,
                horario,
                tutor,
                mailTutor
        );
        comision.setNumero(numero);
        comision.setTurno(turno);
        comision.setAlumnosDni(alumnosDni);
        return comision;
    }
}
