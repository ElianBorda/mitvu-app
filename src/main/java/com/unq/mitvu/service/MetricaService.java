package com.unq.mitvu.service;

import com.unq.mitvu.model.MotivoBaja;
import com.unq.mitvu.model.TipoDeAsistencia;

public interface MetricaService {
    Integer cantidadDeEstudiantesDadosDeBaja();
    Integer cantidadTotalDeEstudiantes();
    Integer cantidadDeEstudiantesActivos();
    Integer cantidadDeEstudiantesDadosDeBajaPorMotivo(MotivoBaja motivo);

    Integer cantidadDeEstudiantesDadosDeBajaDeUnaComision(String idComision);
    Integer cantidadTotalDeEstudiantesDeUnaComision(String idComision);
    Integer cantidadTotalDeEstudiantesActivosDeUnaComision(String idComision);
    Integer cantidadTotalDeEstudianesDadosDeBajaPorMotivoDeUnaComision(String idComision, MotivoBaja motivo);
    Integer cantidadTotalDeEstudiantesQueSeTomoAsistenciaEnElEvento(String idEvento);

    public Integer porcentajeDeTipoDeAsistenciaGlobal(String idEvento, TipoDeAsistencia tipoDeAsistencia);
    public Integer porcentajeDeTipoDeAsistenciaPorComision(String idComision, String idEvento, TipoDeAsistencia tipoDeAsistencia);

}
