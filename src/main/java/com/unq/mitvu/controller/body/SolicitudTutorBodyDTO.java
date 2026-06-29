package com.unq.mitvu.controller.body;

import com.unq.mitvu.model.EstadoAcademico;
import com.unq.mitvu.model.EstadoDiplomatura;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitudTutorBodyDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    private String correo;
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;

    @NotBlank(message = "La carrera es obligatoria")
    private String carrera;
    @NotNull(message = "El estado académico es obligatorio")
    private EstadoAcademico estadoAcademico;
    @NotNull(message = "Debe indicar si tiene experiencia como egresado")
    private Boolean experienciaComoEgresado;
    @NotNull(message = "Debe indicar si fue tutor anteriormente")
    private Boolean fueTutorAnteriormente;
    @NotNull(message = "El estado de la diplomatura es obligatorio")
    private EstadoDiplomatura estadoDiplomatura;
}