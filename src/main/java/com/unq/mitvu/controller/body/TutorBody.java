package com.unq.mitvu.controller.body;

import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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
    private List<String> comisiones_ids;

    static public TutorBody fromTutor(Tutor tutor) {
        return new TutorBody(
                tutor.getId(),
                tutor.getApellido(),
                tutor.getNombre(),
                tutor.getDni(),
                tutor.getMail(),
                tutor.getComisiones().stream().map(Comision::getId).toList()
        );
    }

    public Tutor toTutor() {
        return new Tutor(
                apellido,
                nombre,
                dni,
                mail
        );
    }

    /*
    private ArrayList<ComisionDeTutorDraft> comisiones;

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

    public ArrayList<Comision> toComisiones(ArrayList<ComisionDeTutorDraft> comisionesEstudianteDraft){
        ArrayList<Comision> comisiones = new ArrayList<>();

        for (ComisionDeTutorDraft comisionDeTutorDraft : comisionesEstudianteDraft) {
            comisiones.add(toComision(comisionDeTutorDraft));
        }

        return comisiones;
    }

    public Comision toComision(ComisionDeTutorDraft comisionDeTutorDraft){
        if (comisionDeTutorDraft == null) {
            return null;
        }

        return new Comision(
                this.toEstudiantes(comisionDeTutorDraft.getEstudiantes()),
                new Horario(
                        Integer.parseInt(comisionDeTutorDraft.getHorarioFin().split(":")[0]),
                        Integer.parseInt(comisionDeTutorDraft.getHorarioFin().split(":")[1])
                ),
                new Horario(
                        Integer.parseInt(comisionDeTutorDraft.getHorarioInicio().split(":")[0]),
                        Integer.parseInt(comisionDeTutorDraft.getHorarioInicio().split(":")[1])
                ),
                comisionDeTutorDraft.getAula(),
                comisionDeTutorDraft.getCarrera(),
                comisionDeTutorDraft.getDepartamento(),
                comisionDeTutorDraft.getLocalidad(),
                comisionDeTutorDraft.getNumero()
        );
    }

    static public ArrayList<ComisionDeTutorDraft> toComisionEstudianteDraft(ArrayList<Comision> comisiones) {
        ArrayList<ComisionDeTutorDraft> comisionesDraft = new ArrayList<>();

        for (Comision comision : comisiones) {
            ComisionDeTutorDraft comisionDraft = new ComisionDeTutorDraft(
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

     */
}
