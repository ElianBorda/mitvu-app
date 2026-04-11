package com.unq.mitvu.body;

import com.unq.mitvu.body.drafts.EstudianteDraft;
import com.unq.mitvu.body.drafts.TutorDraft;
import com.unq.mitvu.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.unq.mitvu.body.drafts.ComisionEstudianteDraft.toEstudiantesDraft;
import static com.unq.mitvu.body.drafts.TutorDraft.toTutorDraft;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComisionBody {

    private String id;
    private Integer numero;
    private String localidad;
    private String departamento;
    private String carrera;
    private String aula;
    private String horarioInicio;
    private String horarioFin;
    private TutorDraft tutor;
    private List<EstudianteDraft> estudiantes;


    static public ComisionBody fromComision(Comision comision){
        return new ComisionBody(
                comision.getId(),
                comision.getNumero(),
                comision.getLocalidad(),
                comision.getDepartamento(),
                comision.getCarrera(),
                comision.getAula(),
                comision.getHorarioInicio().toString(),
                comision.getHorarioFin().toString(),
                toTutorDraft(comision.getTutor()),
                toEstudiantesDraft(comision.getEstudiantes())
        );
    }

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
                this.toTutor(this.tutor),
                horarioFinConvertido,
                horarioInicioConvertido,
                this.aula,
                this.carrera,
                this.departamento,
                this.localidad,
                this.numero
        );
    }

    public Tutor toTutor(TutorDraft tutorDraft){
        if (tutorDraft == null){
            return null;
        }

        return (
                new Tutor(
                tutorDraft.getApellido(),
                tutorDraft.getNombre(),
                tutorDraft.getDni(),
                tutorDraft.getMail()
        ));
    }
}
