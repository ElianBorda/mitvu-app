package com.unq.mitvu.body;

import com.unq.mitvu.body.drafts.ComisionDeEstudianteDraft;
import com.unq.mitvu.body.drafts.TutorDraft;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Horario;
import com.unq.mitvu.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.unq.mitvu.body.drafts.ComisionDeEstudianteDraft.toComisionTutorDraft;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EstudianteBody {

    private String id;
    private String apellido;
    private String nombre;
    private String dni;
    private String carrera;
    private Integer cantidadAsistencias;
    private String comision_id;

    static public EstudianteBody fromEstudiante(Estudiante estudiante) {
        return new  EstudianteBody(
                estudiante.getId(),
                estudiante.getApellido(),
                estudiante.getNombre(),
                estudiante.getDni(),
                estudiante.getCarrera(),
                estudiante.getCantidadAsistencias(),
                estudiante.getComision() != null ? estudiante.getComision().getId() : null
        );
    }

    public Estudiante toEstudiante() {
        return new Estudiante(
                apellido,
                nombre,
                dni,
                carrera,
                cantidadAsistencias
        );
    }

    /*
    private ComisionDeEstudianteDraft comision;


    static public EstudianteBody fromEstudiante(Estudiante estudiante){
        return new EstudianteBody(
                estudiante.getId(),
                estudiante.getApellido(),
                estudiante.getNombre(),
                estudiante.getDni(),
                estudiante.getCarrera(),
                estudiante.getCantidadAsistencias(),
                toComisionTutorDraft(estudiante.getComision())
        );
    }

    public Estudiante toEstudiante(){
        return new Estudiante(
                this.apellido,
                this.nombre,
                this.dni,
                this.carrera,
                this.toComision(this.comision),
                this.cantidadAsistencias
        );
    }

    public Comision toComision(ComisionDeEstudianteDraft comisionDeEstudianteDraft){
        if (comisionDeEstudianteDraft == null) {
            return null;
        }

        return new Comision(
                this.toTutor(comisionDeEstudianteDraft.getTutor()),
                new Horario(
                        Integer.parseInt(comisionDeEstudianteDraft.getHorarioFin().split(":")[0]),
                        Integer.parseInt(comisionDeEstudianteDraft.getHorarioFin().split(":")[1])
                ),
                new Horario(
                        Integer.parseInt(comisionDeEstudianteDraft.getHorarioInicio().split(":")[0]),
                        Integer.parseInt(comisionDeEstudianteDraft.getHorarioInicio().split(":")[1])
                ),
                comisionDeEstudianteDraft.getAula(),
                comisionDeEstudianteDraft.getCarrera(),
                comisionDeEstudianteDraft.getDepartamento(),
                comisionDeEstudianteDraft.getLocalidad(),
                comisionDeEstudianteDraft.getNumero()
        );
    }

    public Tutor toTutor(TutorDraft tutorDraft){
        if (tutorDraft == null) {
            return null;
        }

        return new Tutor(
                tutorDraft.getApellido(),
                tutorDraft.getNombre(),
                tutorDraft.getDni(),
                tutorDraft.getMail()
        );
    }

     */


}
