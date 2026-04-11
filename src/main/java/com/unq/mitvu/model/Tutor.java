package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tutores")
public class Tutor {

    @Id
    private String id;
    private String apellido;
    private String nombre;
    private String dni;
    private String mail;
    private ArrayList<Comision> comisiones = new ArrayList<>();

    public Tutor (String nombre) {
        this.nombre = nombre;
    }

    public Tutor(String apellido, String nombre, String dni, String mail, ArrayList<Comision> comisiones) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.mail = mail;
        this.comisiones = comisiones;
    }

    public Tutor(String apellido, String nombre, String dni, String mail) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.mail = mail;
    }

    public void addComision(Comision comision){
        this.comisiones.add(comision);
    }
}
