package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Comision {

    @Id
    private String id;
    private String nombre;

    public Comision(String nombre) {
        this.nombre = nombre;
    }
}
