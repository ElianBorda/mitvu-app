package com.unq.mitvu.controller;

import com.unq.mitvu.controller.dto.MetricaBajaDTO;
import com.unq.mitvu.service.MetricaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metricas")
@CrossOrigin(origins = "*")
public class MetricaController {

    @Autowired MetricaService metricaService;

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
}
