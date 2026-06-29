package com.unq.mitvu.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unq.mitvu.model.EstadoAcademico;
import com.unq.mitvu.model.EstadoDiplomatura;
import com.unq.mitvu.model.EstadoSolicitud;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudTutorDTO {
    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String dni;
    private String carrera;
    private EstadoAcademico estadoAcademico;
    private Boolean experienciaComoEgresado;
    private Boolean fueTutorAnteriormente;
    private EstadoDiplomatura estadoDiplomatura;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaPostulacion;
    private EstadoSolicitud estadoSolicitud;
}