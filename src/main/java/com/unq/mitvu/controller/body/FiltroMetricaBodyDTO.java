package com.unq.mitvu.controller.body;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unq.mitvu.model.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class FiltroMetricaBodyDTO {
    @NotBlank(message = "El tipo de métrica es obligatoria")
    private TipoMetrica tipoMetrica;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaFin;
    private String idComision;
    @NotBlank(message = "El la agrupación de métrica es obligatoria")
    private Agrupacion agruparPor;
    private TipoDeAsistencia tipoDeAsistencia;
    private MotivoBaja motivoBaja;
    @NotBlank(message = "El tipo de calculo es obligatorio")
    private TipoCalculo tipoCalculo;
    private String localidad;
}