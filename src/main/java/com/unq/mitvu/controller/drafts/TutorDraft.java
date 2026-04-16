package com.unq.mitvu.controller.drafts;


import com.unq.mitvu.model.Tutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TutorDraft {
    private String id;
    private String apellido;
    private String nombre;
    private String dni;
    private String mail;

    static public TutorDraft toTutorDraft(Tutor tutor){
        if (tutor == null) {
            return null;
        }

        return new TutorDraft(
                tutor.getId(),
                tutor.getApellido(),
                tutor.getNombre(),
                tutor.getDni(),
                tutor.getMail()
        );
    }
}
