package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "anuncios")
public class Anuncio {
    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaDeCreacion;
    private String idComision;
    private String creadoPorId;

}
