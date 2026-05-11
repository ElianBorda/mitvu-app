package com.unq.mitvu.model;

import com.unq.mitvu.controller.dto.AsistenciaDTO;
import lombok.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
public class Asistencia {
    @NonNull private LocalDate fecha;
    @NonNull private TipoDeAsistencia tipoDeAsistencia;
    private String observacion;

    public Asistencia() {}

    public Asistencia(LocalDate fecha, TipoDeAsistencia asistio, String observacion) {
        this.fecha = fecha;
        this.tipoDeAsistencia = asistio;
        this.observacion = observacion;
    }

    public AsistenciaDTO aAsistenciaDTO() {
        return new AsistenciaDTO(fecha, tipoDeAsistencia, observacion);
    }
}
