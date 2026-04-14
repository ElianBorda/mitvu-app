package com.unq.mitvu.body.drafts;

import com.unq.mitvu.model.Comision;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.unq.mitvu.body.drafts.TutorDraft.toTutorDraft;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComisionDeEstudianteDraft {

    private String id;
    private Integer numero;
    private String localidad;
    private String departamento;
    private String carrera;
    private String aula;
    private String horarioInicio;
    private String horarioFin;
    private TutorDraft tutor;

    static public ComisionDeEstudianteDraft toComisionTutorDraft(Comision comision){
        if (comision == null) {
            return null;
        }

        return new ComisionDeEstudianteDraft(
                comision.getId(),
                comision.getNumero(),
                comision.getLocalidad(),
                comision.getDepartamento(),
                comision.getCarrera(),
                comision.getAula(),
                comision.getHorarioInicio().toString(),
                comision.getHorarioFin().toString(),
                toTutorDraft(comision.getTutor())
        );
    }
}
