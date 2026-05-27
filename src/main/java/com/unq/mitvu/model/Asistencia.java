package com.unq.mitvu.model;

import com.unq.mitvu.controller.dto.AsistenciaDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
public class Asistencia {
    private LocalDate fecha;
    private TipoDeAsistencia tipoDeAsistencia;
    private String observacion;

    public Asistencia(LocalDate fecha, TipoDeAsistencia asistio, String observacion) {
        this.fecha = fecha;
        this.tipoDeAsistencia = asistio;
        this.observacion = observacion;
    }

    public AsistenciaDTO aAsistenciaDTO() {
        return new AsistenciaDTO(fecha, tipoDeAsistencia, observacion);
    }
}
