package com.unq.mitvu.body;

import com.unq.mitvu.body.drafts.ComisionEstudianteDraft;
import com.unq.mitvu.body.drafts.ComisionTutorDraft;
import com.unq.mitvu.body.drafts.EstudianteDraft;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Horario;
import com.unq.mitvu.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

import static com.unq.mitvu.body.drafts.ComisionEstudianteDraft.toEstudiantesDraft;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TutorBody {

    private String id;
    private String apellido;
    private String nombre;
    private String dni;
    private String mail;
    private ArrayList<ComisionEstudianteDraft> comisiones;

    static public TutorBody fromTutor(Tutor tutor){
        return new TutorBody(
                tutor.getId(),
                tutor.getApellido(),
                tutor.getNombre(),
                tutor.getDni(),
                tutor.getMail(),
                toComisionEstudianteDraft(tutor.getComisiones())
        );
    }

    public Tutor toTutor(){
        return new Tutor(
                this.apellido,
                this.nombre,
                this.dni,
                this.mail,
                this.toComisiones(this.comisiones)
        );
    }

    public ArrayList<Comision> toComisiones(ArrayList<ComisionEstudianteDraft> comisionesEstudianteDraft){
        ArrayList<Comision> comisiones = new ArrayList<>();

        for (ComisionEstudianteDraft comisionEstudianteDraft : comisionesEstudianteDraft) {
            comisiones.add(toComision(comisionEstudianteDraft));
        }

        return comisiones;
    }

    public Comision toComision(ComisionEstudianteDraft comisionEstudianteDraft){
        if (comisionEstudianteDraft == null) {
            return null;
        }

        return new Comision(
                this.toEstudiantes(comisionEstudianteDraft.getEstudiantes()),
                new Horario(
                        Integer.parseInt(comisionEstudianteDraft.getHorarioFin().split(":")[0]),
                        Integer.parseInt(comisionEstudianteDraft.getHorarioFin().split(":")[1])
                ),
                new Horario(
                        Integer.parseInt(comisionEstudianteDraft.getHorarioInicio().split(":")[0]),
                        Integer.parseInt(comisionEstudianteDraft.getHorarioInicio().split(":")[1])
                ),
                comisionEstudianteDraft.getAula(),
                comisionEstudianteDraft.getCarrera(),
                comisionEstudianteDraft.getDepartamento(),
                comisionEstudianteDraft.getLocalidad(),
                comisionEstudianteDraft.getNumero()
        );
    }

    static public ArrayList<ComisionEstudianteDraft> toComisionEstudianteDraft(ArrayList<Comision> comisiones) {
        ArrayList<ComisionEstudianteDraft> comisionesDraft = new ArrayList<>();

        for (Comision comision : comisiones) {
            ComisionEstudianteDraft comisionDraft = new ComisionEstudianteDraft(
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
            comisionesDraft.add(comisionDraft);
        }
        return comisionesDraft;
    }

    public ArrayList<Estudiante> toEstudiantes(ArrayList<EstudianteDraft> estudiantesDraft){
        ArrayList<Estudiante> estudiantes = new ArrayList<>();

        for (EstudianteDraft estudianteDraft : estudiantesDraft) {
            Estudiante estudiante = new Estudiante(
                    estudianteDraft.getCantidadAsistencias(),
                    estudianteDraft.getApellido(),
                    estudianteDraft.getNombre(),
                    estudianteDraft.getDni(),
                    estudianteDraft.getCarrera());
            estudiantes.add(estudiante);
        }

        return estudiantes;
    };
}
