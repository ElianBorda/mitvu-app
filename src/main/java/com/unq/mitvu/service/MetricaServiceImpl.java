package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.dao.EventoDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.model.*;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MetricaServiceImpl implements MetricaService{

    private final EstudianteDAO estudianteDAO;
    private final ComisionDAO comisionDAO;
    private final TutorDAO tutorDAO;
    private final EventoDAO eventoDAO;
    private final MongoTemplate mongoTemplate;

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
            return 0;
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
    public List<DataPoint> generarMetrica(FiltroMetrica filtro) {
        if (filtro.getTipoMetrica() == TipoMetrica.BAJA) {
            return analizarBajas(filtro);
        } else if (filtro.getTipoMetrica() == TipoMetrica.ASISTENCIA) {
            return analizarAsistencias(filtro);
        }
        return new ArrayList<>();
    }

    private List<DataPoint> analizarBajas(FiltroMetrica filtro) {

        long totalEstudiantes = 0;
        if (filtro.getTipoCalculo() == TipoCalculo.PORCENTAJE) {
            Criteria criteriaBase = new Criteria();
            if (filtro.getIdComision() != null && !filtro.getIdComision().isEmpty()) {
                criteriaBase.and("comision").is(new ObjectId(filtro.getIdComision()));
            }
            totalEstudiantes = mongoTemplate.count(Query.query(criteriaBase), "estudiantes");
            if (totalEstudiantes == 0) return new ArrayList<>();
        }

        List<AggregationOperation> pipeline = new ArrayList<>();

        Criteria criteria = Criteria.where("baja").exists(true);
        if (filtro.getIdComision() != null && !filtro.getIdComision().isEmpty()) {
            criteria.and("baja.idComisionDadoDeBaja").is(filtro.getIdComision());
        }
        if (filtro.getMotivoBaja() != null) {
            criteria.and("baja.motivo").is(filtro.getMotivoBaja());
        }
        if (filtro.getFechaInicio() != null && filtro.getFechaFin() != null) {
            criteria.and("baja.fechaBaja").gte(filtro.getFechaInicio()).lte(filtro.getFechaFin());
        }
        pipeline.add(Aggregation.match(criteria));

        String campoAgrupacion = "baja.motivo";

        if (filtro.getAgruparPor() == Agrupacion.COMISION) {
            campoAgrupacion = "baja.idComisionDadoDeBaja";
        } else if (filtro.getAgruparPor() == Agrupacion.FECHA) {
            campoAgrupacion = "baja.fechaBaja";
        } else if (filtro.getAgruparPor() == Agrupacion.ANIO) {
            pipeline.add(Aggregation.project().and(DateOperators.Year.yearOf("baja.fechaBaja")).as("campoTemporalAnio"));
            campoAgrupacion = "campoTemporalAnio";
        }

        pipeline.add(Aggregation.group(campoAgrupacion).count().as("cantidadTotal"));

        ProjectionOperation projectStage = Aggregation.project();

        if (filtro.getAgruparPor() == Agrupacion.FECHA) {
            projectStage = projectStage.and(DateOperators.DateToString.dateOf("_id").toString("%d-%m-%Y")).as("etiqueta");
        } else {
            projectStage = projectStage.andExpression("toString(_id)").as("etiqueta");
        }

        if (filtro.getTipoCalculo() == TipoCalculo.PORCENTAJE) {
            projectStage = projectStage.andExpression("(cantidadTotal * 100) / [0]", totalEstudiantes).as("valor");
        } else {
            projectStage = projectStage.and("cantidadTotal").as("valor");
        }

        pipeline.add(projectStage);

        Aggregation aggregation = Aggregation.newAggregation(pipeline);
        return mongoTemplate.aggregate(aggregation, "estudiantes", DataPoint.class).getMappedResults();
    }

    private List<DataPoint> analizarAsistencias(FiltroMetrica filtro) {

        List<AggregationOperation> pipeline = new ArrayList<>();

        Criteria criteriaInicial = new Criteria();
        if (filtro.getIdComision() != null && !filtro.getIdComision().isEmpty()) {
            criteriaInicial = Criteria.where("comision").is(new ObjectId(filtro.getIdComision()));
        }
        pipeline.add(Aggregation.match(criteriaInicial));

        pipeline.add(Aggregation.unwind("asistencias"));

        Criteria criteriaSecundario = new Criteria();
        if (filtro.getFechaInicio() != null && filtro.getFechaFin() != null) {
            criteriaSecundario.and("asistencias.fecha").gte(filtro.getFechaInicio()).lte(filtro.getFechaFin());
        }

        if (filtro.getTipoCalculo() == TipoCalculo.CANTIDAD && filtro.getTipoDeAsistencia() != null) {
            criteriaSecundario.and("asistencias.tipoDeAsistencia").is(filtro.getTipoDeAsistencia());
        }
        pipeline.add(Aggregation.match(criteriaSecundario));

        ProjectionOperation preGroupProject = Aggregation.project()
                .and("asistencias.tipoDeAsistencia").as("tipoAsistenciaTemporal");

        if (filtro.getAgruparPor() == Agrupacion.FECHA) {
            preGroupProject = preGroupProject.and("asistencias.fecha").as("campoAgrupacion");
        } else if (filtro.getAgruparPor() == Agrupacion.ANIO) {
            preGroupProject = preGroupProject.and(DateOperators.Year.yearOf("asistencias.fecha")).as("campoAgrupacion");
        } else {
            preGroupProject = preGroupProject.and("asistencias.tipoDeAsistencia").as("campoAgrupacion");
        }
        pipeline.add(preGroupProject);

        GroupOperation groupStage = Aggregation.group("campoAgrupacion");

        if (filtro.getTipoCalculo() == TipoCalculo.PORCENTAJE) {
            String tipoObjetivo = filtro.getTipoDeAsistencia() != null ?
                    filtro.getTipoDeAsistencia().name() : TipoDeAsistencia.PRESENTE.name();

            ConditionalOperators.Cond esObjetivo = ConditionalOperators
                    .when(ComparisonOperators.valueOf("tipoAsistenciaTemporal").equalToValue(tipoObjetivo))
                    .then(1).otherwise(0);

            groupStage = groupStage.count().as("totalCasos").sum(esObjetivo).as("casosPositivos");
        } else {
            groupStage = groupStage.count().as("cantidadTotal");
        }
        pipeline.add(groupStage);

        ProjectionOperation finalProject = Aggregation.project();

        if (filtro.getAgruparPor() == Agrupacion.FECHA) {
            finalProject = finalProject.and(DateOperators.DateToString.dateOf("_id").toString("%d-%m-%Y")).as("etiqueta");
        } else {
            finalProject = finalProject.andExpression("toString(_id)").as("etiqueta");
        }

        if (filtro.getTipoCalculo() == TipoCalculo.PORCENTAJE) {
            finalProject = finalProject.andExpression("(casosPositivos * 100) / totalCasos").as("valor");
        } else {
            finalProject = finalProject.and("cantidadTotal").as("valor");
        }
        pipeline.add(finalProject);

        Aggregation aggregation = Aggregation.newAggregation(pipeline);
        return mongoTemplate.aggregate(aggregation, "estudiantes", DataPoint.class).getMappedResults();
    }


    @Override
    public Integer cantidadDeEstudiantesDadosDeBajaPorMotivo(MotivoBaja motivo) {
        return estudianteDAO.countEstudianteByBaja_Motivo(motivo);
    }
}
