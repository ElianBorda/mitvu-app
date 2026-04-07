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
    private ArrayList<String> comisiones_ids;

    public Tutor (String nombre) {
        this.nombre = nombre;
    }

    public Tutor (String apellido, String nombre, String dni,ArrayList<String> comisiones_ids){
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.comisiones_ids = comisiones_ids;
    }

}
