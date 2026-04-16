package com.unq.mitvu.controller.body;

import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Horario;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ComisionBody {

    private String localidad;
    private String departamento;
    private String carrera;
    private String aula;
    private String horarioInicio;
    private String horarioFin;

    public Comision toComision(){

        Horario horarioInicioConvertido = new Horario(
                Integer.parseInt(this.horarioInicio.split(":")[0]),
                Integer.parseInt(this.horarioInicio.split(":")[1])
        );
        Horario horarioFinConvertido = new Horario(
                Integer.parseInt(this.horarioFin.split(":")[0]),
                Integer.parseInt(this.horarioFin.split(":")[1])
        );

        return new Comision(
                this.localidad,
                this.departamento,
                this.carrera,
                this.aula,
                horarioInicioConvertido,
                horarioFinConvertido
        );
    }
}
