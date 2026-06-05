package com.unq.mitvu.controller;

import com.unq.mitvu.controller.dto.DataPointDTO;
import com.unq.mitvu.controller.body.FiltroMetricaBodyDTO;
import com.unq.mitvu.controller.dto.MetricaAsistenciaDTO;
import com.unq.mitvu.controller.dto.MetricaBajaDTO;
import com.unq.mitvu.mapper.EventoMapper;
import com.unq.mitvu.mapper.MetricaMapper;
import com.unq.mitvu.model.DataPoint;
import com.unq.mitvu.model.Evento;
import com.unq.mitvu.model.FiltroMetrica;
import com.unq.mitvu.model.TipoDeAsistencia;
import com.unq.mitvu.service.EventoService;
import com.unq.mitvu.service.MetricaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/metricas")
@CrossOrigin(origins = "*")
public class MetricaController {

    @Autowired MetricaService metricaService;
    @Autowired EventoService eventoService;
    @Autowired EventoMapper eventoMapper;
    @Autowired MetricaMapper metricaMapper;

    @GetMapping("/estudiantes/dadosDeBaja")
    public ResponseEntity<MetricaBajaDTO> obtenerMetricasDeBajaDeTodosLosEstudiantes(){
        return ResponseEntity.ok(
                MetricaBajaDTO.builder()
                    .cantidadDeEstudiantesActivos(metricaService.cantidadDeEstudiantesActivos())
                    .cantidadDeEstudiantesDadoDeBaja(metricaService.cantidadDeEstudiantesDadosDeBaja())
                    .cantidadTotalDeEstudiantes(metricaService.cantidadTotalDeEstudiantes())
                    .build()
        );
    }

    @GetMapping("/estudiantes/dadosDeBaja/porComision/{idComision}")
    public ResponseEntity<MetricaBajaDTO> obtenerMetricasDeBajaDeUnaComision(@PathVariable String idComision){
        return ResponseEntity.ok(
                MetricaBajaDTO.builder()
                        .cantidadTotalDeEstudiantes(metricaService.cantidadTotalDeEstudiantesDeUnaComision(idComision))
                        .cantidadDeEstudiantesActivos(metricaService.cantidadTotalDeEstudiantesActivosDeUnaComision(idComision))
                        .cantidadDeEstudiantesDadoDeBaja(metricaService.cantidadDeEstudiantesDadosDeBajaDeUnaComision(idComision))
                        .build()
        );
    }

    @GetMapping("/asistencia")
    public ResponseEntity<List<MetricaAsistenciaDTO>> obtenerMetricasDeAsistenciaGlobal(){
        List<Evento> eventos = eventoService.obtenerTodosLosEventosGlobales();
        List<MetricaAsistenciaDTO> metricaAsistenciaDTOS = new ArrayList<>();
        for (Evento evento : eventos){
            metricaAsistenciaDTOS.add(
                    MetricaAsistenciaDTO.builder()
                            .porcentajeAsistencia(metricaService.porcentajeDeTipoDeAsistenciaGlobal(evento.getId(), TipoDeAsistencia.PRESENTE))
                            .porcentajeFalta(metricaService.porcentajeDeTipoDeAsistenciaGlobal(evento.getId(), TipoDeAsistencia.AUSENTE))
                            .porcentajeFaltaJustificada(metricaService.porcentajeDeTipoDeAsistenciaGlobal(evento.getId(), TipoDeAsistencia.AUSENCIA_JUSTIFICADA))
                            .evento(eventoMapper.aEventoDTO(evento))
                            .build()
            );
        }
        return ResponseEntity.ok(metricaAsistenciaDTOS);
    };

    @GetMapping("/asistencia/comision/{id}")
    public ResponseEntity<List<MetricaAsistenciaDTO>> obtenerMetricasDeAsistenciaParaComision(@PathVariable String id){
        List<Evento> eventos = eventoService.obtenerTodosLosEventosGlobales();
        List<MetricaAsistenciaDTO> metricaAsistenciaDTOS = new ArrayList<>();
        for (Evento evento : eventos){
            metricaAsistenciaDTOS.add(
                    MetricaAsistenciaDTO.builder()
                            .porcentajeAsistencia(metricaService.porcentajeDeTipoDeAsistenciaPorComision(id, evento.getId(), TipoDeAsistencia.PRESENTE))
                            .porcentajeFalta(metricaService.porcentajeDeTipoDeAsistenciaPorComision(id, evento.getId(), TipoDeAsistencia.AUSENTE))
                            .porcentajeFaltaJustificada(metricaService.porcentajeDeTipoDeAsistenciaPorComision(id, evento.getId(), TipoDeAsistencia.AUSENCIA_JUSTIFICADA))
                            .evento(eventoMapper.aEventoDTO(evento))
                            .build()
            );
        }
        return ResponseEntity.ok(metricaAsistenciaDTOS);
    };

    @PostMapping("/dinamicas")
    public ResponseEntity<List<DataPointDTO>> obtenerMetricasDinamicas(@RequestBody FiltroMetricaBodyDTO filtrosDTO) {
        FiltroMetrica filtrosModelo = metricaMapper.aFiltroMetrica(filtrosDTO);
        List<DataPoint> resultadosModelo = metricaService.generarMetrica(filtrosModelo);
        List<DataPointDTO> respuestaDTO = metricaMapper.aListaDeDataPointDTO(resultadosModelo);

        return ResponseEntity.ok(respuestaDTO);
    }
}
