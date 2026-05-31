package com.unq.mitvu.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnuncioDTO {

    private String titulo;
    private String descripcion;
    private LocalDate fechaDeCreacion;
    private String idComision;
    private String creadoPorId;

}
