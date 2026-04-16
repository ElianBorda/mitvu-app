package com.unq.mitvu.controller.drafts;

import com.unq.mitvu.model.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDraft {
    private String id;
    private String apellido;
    private String nombre;
    private String dni;
    private String carrera;
    private Integer cantidadAsistencias;

    static public EstudianteDraft toEstudianteDraft(Estudiante estudiante){
        if (estudiante == null) {
            return null;
        }

        return new EstudianteDraft(
                estudiante.getId(),
                estudiante.getApellido(),
                estudiante.getNombre(),
                estudiante.getDni(),
                estudiante.getCarrera(),
                estudiante.getCantidadAsistencias()
        );
    }
}
