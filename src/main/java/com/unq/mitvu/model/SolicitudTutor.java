package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "solicitudes_tutores")
public class SolicitudTutor {

    @Id
    private String id;

    // Datos Personales
    private String nombre;
    private String apellido;
    private String correo;
    private String dni;

    // Datos Académicos y Experiencia
    private String carrera;
    private EstadoAcademico estadoAcademico;
    private Boolean experienciaComoEgresado;
    private Boolean fueTutorAnteriormente;
    private EstadoDiplomatura estadoDiplomatura;

    // Metadatos
    private LocalDateTime fechaPostulacion;
    private EstadoSolicitud estadoSolicitud;
}