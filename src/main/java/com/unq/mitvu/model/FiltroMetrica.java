package com.unq.mitvu.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FiltroMetrica {
    private TipoMetrica tipoMetrica;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String idComision;
    private Agrupacion agruparPor;
    private TipoDeAsistencia tipoDeAsistencia;
    private MotivoBaja motivoBaja;
    private TipoCalculo tipoCalculo;
    private String localidad;
}