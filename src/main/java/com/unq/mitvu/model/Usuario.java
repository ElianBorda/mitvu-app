package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    protected String id;
    protected String apellido;
    protected String nombre;
    protected String dni;
    protected String mail;
    protected String password;
    protected Rol rol;

    public Usuario(String apellido, String nombre, String dni, String mail, Rol rol) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.mail = mail;
        this.rol = rol;
    }

    public Usuario(String apellido, String nombre, String dni, String mail, String password, Rol rol) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.mail = mail;
        this.password = password;
        this.rol = rol;
    }
}
