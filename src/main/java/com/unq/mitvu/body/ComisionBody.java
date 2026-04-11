package com.unq.mitvu.body;

import com.unq.mitvu.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComisionBody {

    private Integer numero;
    private String localidad;
    private String departamento;
    private String carrera;
    private String aula;
    private String horarioInicio;
    private String horarioFin;
    private Tutor tutor;

    public static ComisionBody fromComision(Comision comision) {
        return new ComisionBody(
                comision.getNumero(),
                comision.getLocalidad(),
                comision.getDepartamento(),
                comision.getCarrera(),
                comision.getAula(),
                comision.getHorarioInicio().toString(),
                comision.getHorarioFin().toString(),
                comision.getTutor()
        );
    }

    public Comision toComision() {
        Horario horarioInicioConvertido = new Horario(
                Integer.parseInt(this.horarioInicio.split(":")[0]),
                Integer.parseInt(this.horarioInicio.split(":")[1])
        );
        Horario horarioFinConvertido = new Horario(
                Integer.parseInt(this.horarioFin.split(":")[0]),
                Integer.parseInt(this.horarioFin.split(":")[1])
        );

        return new Comision(
                this.tutor,
                horarioFinConvertido,
                horarioInicioConvertido,
                this.aula,
                this.carrera,
                this.departamento,
                this.localidad,
                this.numero
        );
    }
}
