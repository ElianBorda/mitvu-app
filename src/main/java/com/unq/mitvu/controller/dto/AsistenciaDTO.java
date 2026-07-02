package com.unq.mitvu.controller.dto;

import com.unq.mitvu.model.Asistencia;
import com.unq.mitvu.model.TipoDeAsistencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AsistenciaDTO {

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    @NotNull(message = "El tipo de asistencia es obligatorio")
    private TipoDeAsistencia tipoDeAsistencia;
    private String observacion;

    public AsistenciaDTO() {}

    public AsistenciaDTO(LocalDate fecha, TipoDeAsistencia tipoDeAsistencia, String observacion) {
        this.fecha = fecha;
        this.tipoDeAsistencia = tipoDeAsistencia;
        this.observacion = observacion;
    }

    public Asistencia aAsistenciaModelo() {
        return new Asistencia(this.fecha, this.tipoDeAsistencia, this.observacion);
    }
}