package com.unq.mitvu.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "administradores")
public class Administrador extends Usuario{

    public Administrador(String id, String apellido, String nombre, String dni, String mail, String password) {
        super(id, apellido, nombre, dni, mail, password, Rol.ADMIN);
    }

    public Administrador(String apellido, String nombre, String dni, String mail) {
        super(apellido, nombre, dni, mail, Rol.ADMIN);
    }

    public Administrador(String apellido, String nombre, String dni, String mail, String password) {
        super(apellido, nombre, dni, mail, password, Rol.ADMIN);
    }
}
