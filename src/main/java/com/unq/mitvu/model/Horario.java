package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class Horario {
    private Integer hora;
    private Integer minuto;

    public Turno definirTurnoConHorarioFinal(Horario horarioFinal){
        if (hora >= 8 && horarioFinal.getHora() < 12) {
            return Turno.MANANA;
        } else if (hora >= 12 && horarioFinal.getHora() < 18) {
            return Turno.TARDE;
        } else {
            return Turno.NOCHE;
        }
    }

    @Override
    public String toString() {

        String horaString = this.hora < 10 ? "0" + this.hora : "" + this.hora;
        String minutoString = this.minuto < 10 ? "0" + this.minuto : "" + this.minuto;

        return horaString + ":" + minutoString;
    }
}



