package com.unq.mitvu.controller.body;


import java.time.LocalDateTime;

public class EventoBodyDTO {
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha;
    private String idComision;
    private boolean generarParaTodos = false;
}
