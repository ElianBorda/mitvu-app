package com.unq.mitvu.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "notificaciones")
public class Notificacion {
    @Id
    private String id;
    private String idUsuario;
    private String titulo;
    private String cuerpo;
    private LocalDateTime fecha;
    private boolean leida = false;
}