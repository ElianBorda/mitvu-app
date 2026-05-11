package com.unq.mitvu.controller.dto;

import com.unq.mitvu.model.Asistencia;
import com.unq.mitvu.model.TipoDeAsistencia;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AsistenciaDTO {
    private LocalDate fecha;
    private TipoDeAsistencia tipoDeAsistencia;
    private String observacion;

    public AsistenciaDTO() {}

    public AsistenciaDTO(@NonNull LocalDate fecha, @NonNull TipoDeAsistencia tipoDeAsistencia, String observacion) {
        this.fecha = fecha;
        this.tipoDeAsistencia = tipoDeAsistencia;
        this.observacion = observacion;
    }

    public Asistencia aAsistenciaModelo() {
        return new Asistencia(this.fecha, this.tipoDeAsistencia, this.observacion);
    }
}