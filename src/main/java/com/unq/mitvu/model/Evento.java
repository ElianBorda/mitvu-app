package com.unq.mitvu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "eventos")
public class Evento {
    @Id
    private String id;
    private String titulo;
    private String descripcion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime fecha;
    private String idComision; // Si es null, es un evento GLOBAL
    private String creadoPorId; // ID del Tutor o Admin que lo creó
    private boolean esGlobal;
}
