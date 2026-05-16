package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.dao.EventoDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.model.Evento;
import com.unq.mitvu.model.MotivoBaja;
import com.unq.mitvu.model.TipoDeAsistencia;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class MetricaServiceImpl implements MetricaService{

    private final EstudianteDAO estudianteDAO;
    private final ComisionDAO comisionDAO;
    private final TutorDAO tutorDAO;
    private final EventoDAO eventoDAO;

    @Override
    public Integer cantidadDeEstudiantesDadosDeBaja() {
        return estudianteDAO.countByBajaIsNotNull();
    }

    @Override
    public Integer cantidadTotalDeEstudiantes() {
        return Math.toIntExact(estudianteDAO.count());
    }

    @Override
    public Integer cantidadDeEstudiantesActivos() {
        return estudianteDAO.countEstudianteByBajaIsNull();
    }

    @Override
    public Integer cantidadDeEstudiantesDadosDeBajaDeUnaComision(String idComision) {
        if (comisionDAO.existsById(idComision)) {
            return estudianteDAO.countEstudianteByBaja_idComisionDadoDeBaja(idComision);
        } else {
            throw new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision);
        }
    }

    @Override
    public Integer cantidadTotalDeEstudiantesDeUnaComision(String idComision) {
        if (comisionDAO.existsById(idComision)) {
            return estudianteDAO.countEstudianteByComision_Id(idComision) + estudianteDAO.countEstudianteByBaja_idComisionDadoDeBaja(idComision);
        } else {
            throw new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision);
        }
    }

    @Override
    public Integer cantidadTotalDeEstudiantesActivosDeUnaComision(String idComision) {
        if (comisionDAO.existsById(idComision)) {
            return estudianteDAO.countEstudiantesByComision_IdAndBajaIsNull(idComision);
        } else {
            throw new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision);
        }
    }

    @Override
    public Integer cantidadTotalDeEstudianesDadosDeBajaPorMotivoDeUnaComision(String idComision, MotivoBaja motivo) {
        if (comisionDAO.existsById(idComision)) {
            return estudianteDAO.countEstudiantesByBaja_idComisionDadoDeBajaAndBaja_Motivo(idComision, motivo);
        } else {
            throw new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision);
        }
    }

    @Override
    public Integer cantidadTotalDeEstudiantesQueSeTomoAsistenciaEnElEvento(String idEvento) {
        return 0;
    }

    @Override
    public Integer porcentajeDeTipoDeAsistenciaGlobal(String idEvento, TipoDeAsistencia tipoDeAsistencia) {
        Evento evento = eventoDAO.findById(idEvento)
                .orElseThrow(() -> new RecursoNoEncontradoException(idEvento, "No se encontró el EVENTO con id: " + idEvento));
        LocalDate fechaDelEvento = evento.getFecha();
        long presentes = estudianteDAO.countEstudiantesPorFechaYTipoAsistencia(fechaDelEvento, tipoDeAsistencia);
        long totalEvaluados = estudianteDAO.countEstudiantesConAsistenciaEnFecha(fechaDelEvento);

        if (totalEvaluados == 0) {
            return 0; // Devolvemos 0 entero
        }
        return (int) Math.round(((double) presentes / totalEvaluados) * 100.0);
    }

    @Override
    public Integer porcentajeDeTipoDeAsistenciaPorComision(String idComision, String idEvento, TipoDeAsistencia tipoDeAsistencia) {
        if (!comisionDAO.existsById(idComision)) {
            throw new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision);
        }
        Evento evento = eventoDAO.findById(idEvento)
                .orElseThrow(() -> new RecursoNoEncontradoException(idEvento, "No se encontró el EVENTO con id: " + idEvento));

        LocalDate fechaDelEvento = evento.getFecha();

        long presentes = estudianteDAO.countEstudiantesDeComisionPorFechaYTipoAsistencia(idComision, fechaDelEvento, tipoDeAsistencia);
        long totalEvaluados = estudianteDAO.countEstudiantesDeComisionConAsistenciaEnFecha(idComision, fechaDelEvento);

        if (totalEvaluados == 0) {
            return 0;
        }

        return (int) Math.round(((double) presentes / totalEvaluados) * 100.0);
    }

    @Override
    public Integer cantidadDeEstudiantesDadosDeBajaPorMotivo(MotivoBaja motivo) {
        return estudianteDAO.countEstudianteByBaja_Motivo(motivo);
    }
}
