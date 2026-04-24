package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "tutores")
public class Tutor extends Usuario {

    public Tutor(String apellido, String nombre, String dni, String mail, String password, Rol rol) {
        super(apellido, nombre, dni, mail, password, rol);
    }
}
