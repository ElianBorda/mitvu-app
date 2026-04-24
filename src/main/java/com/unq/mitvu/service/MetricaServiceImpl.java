package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.model.MotivoBaja;
import org.springframework.stereotype.Service;

@Service
public class MetricaServiceImpl implements MetricaService{

    private final EstudianteDAO estudianteDAO;
    private final ComisionDAO comisionDAO;
    private final TutorDAO tutorDAO;

    public MetricaServiceImpl(EstudianteDAO estudianteDAO, ComisionDAO comisionDAO, TutorDAO tutorDAO) {
        this.estudianteDAO = estudianteDAO;
        this.comisionDAO = comisionDAO;
        this.tutorDAO = tutorDAO;
    }

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
    public Integer cantidadDeEstudiantesDadosDeBajaPorMotivo(MotivoBaja motivo) {
        return estudianteDAO.countEstudianteByBaja_Motivo(motivo);
    }
}
