package com.unq.mitvu.body.drafts;

import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.unq.mitvu.body.drafts.EstudianteDraft.toEstudianteDraft;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComisionDeTutorDraft {

    private String id;
    private Integer numero;
    private String localidad;
    private String departamento;
    private String carrera;
    private String aula;
    private String horarioInicio;
    private String horarioFin;
    private ArrayList<EstudianteDraft> estudiantes;

    static public ComisionDeTutorDraft toComisionEstudianteDraft(Comision comision){
        if (comision == null) {
            return null;
        }

        return new ComisionDeTutorDraft(
                comision.getId(),
                comision.getNumero(),
                comision.getLocalidad(),
                comision.getDepartamento(),
                comision.getCarrera(),
                comision.getAula(),
                comision.getHorarioInicio().toString(),
                comision.getHorarioFin().toString(),
                toEstudiantesDraft(comision.getEstudiantes())
        );
    }

    static public ArrayList<EstudianteDraft> toEstudiantesDraft(List<Estudiante> estudiantes) {
        ArrayList<EstudianteDraft> estudiantesDraft = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            estudiantesDraft.add(toEstudianteDraft(estudiante));
        }

        return estudiantesDraft;
    }
}
