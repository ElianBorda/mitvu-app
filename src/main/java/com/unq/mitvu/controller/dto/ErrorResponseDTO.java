package com.unq.mitvu.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDTO {
    private String mensaje;
    private int codigoEstado;
    private String ruta;
    private LocalDateTime timestamp;
}