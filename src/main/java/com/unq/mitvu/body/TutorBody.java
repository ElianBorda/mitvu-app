package com.unq.mitvu.body;

import com.unq.mitvu.model.Tutor;

import java.util.ArrayList;

public class TutorBody {

    private String apellido;
    private String nombre;
    private String dni;
    private ArrayList<String> comisiones_ids;

    public TutorBody() {}

    public TutorBody(String apellido, String nombre, String dni, ArrayList<String> comisiones_ids) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.comisiones_ids = comisiones_ids;
    }

    public Tutor toTutor() {
        return new Tutor(apellido, nombre, dni, comisiones_ids);
    }

}
