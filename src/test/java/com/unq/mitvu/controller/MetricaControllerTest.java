package com.unq.mitvu.controller;

import com.unq.mitvu.controller.dto.EventoDTO;
import com.unq.mitvu.mapper.EventoMapper;
import com.unq.mitvu.model.Evento;
import com.unq.mitvu.model.TipoDeAsistencia;
import com.unq.mitvu.service.EventoService;
import com.unq.mitvu.service.MetricaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetricaController.class)
@AutoConfigureMockMvc(addFilters = false)
class MetricaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MetricaService metricaService;

    @MockitoBean
    private EventoService eventoService;

    @MockitoBean
    private EventoMapper eventoMapper;

    private Evento eventoMock;
    private EventoDTO eventoDTOMock;

    @BeforeEach
    void setUp() {
        eventoMock = new Evento();
        eventoMock.setId("1");

        eventoDTOMock = new EventoDTO();
        eventoDTOMock.setId("1");
    }

    @Test
    void obtenerMetricasDeBajaDeTodosLosEstudiantes_DeberiaRetornar200() throws Exception {
        when(metricaService.cantidadDeEstudiantesActivos()).thenReturn(10);
        when(metricaService.cantidadDeEstudiantesDadosDeBaja()).thenReturn(2);
        when(metricaService.cantidadTotalDeEstudiantes()).thenReturn(12);

        mockMvc.perform(get("/api/metricas/estudiantes/dadosDeBaja")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadDeEstudiantesActivos").value(10))
                .andExpect(jsonPath("$.cantidadDeEstudiantesDadoDeBaja").value(2))
                .andExpect(jsonPath("$.cantidadTotalDeEstudiantes").value(12));
    }

    @Test
    void obtenerMetricasDeBajaDeUnaComision_DeberiaRetornar200() throws Exception {
        when(metricaService.cantidadTotalDeEstudiantesDeUnaComision(anyString())).thenReturn(15);
        when(metricaService.cantidadTotalDeEstudiantesActivosDeUnaComision(anyString())).thenReturn(14);
        when(metricaService.cantidadDeEstudiantesDadosDeBajaDeUnaComision(anyString())).thenReturn(1);

        mockMvc.perform(get("/api/metricas/estudiantes/dadosDeBaja/porComision/comision1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadTotalDeEstudiantes").value(15))
                .andExpect(jsonPath("$.cantidadDeEstudiantesActivos").value(14))
                .andExpect(jsonPath("$.cantidadDeEstudiantesDadoDeBaja").value(1));
    }

    @Test
    void obtenerMetricasDeAsistenciaGlobal_DeberiaRetornar200YUnaLista() throws Exception {
        when(eventoService.obtenerTodosLosEventosGlobales()).thenReturn(List.of(eventoMock));
        when(eventoMapper.aEventoDTO(any(Evento.class))).thenReturn(eventoDTOMock);
        when(metricaService.porcentajeDeTipoDeAsistenciaGlobal(anyString(), any(TipoDeAsistencia.class))).thenReturn(50);

        mockMvc.perform(get("/api/metricas/asistencia")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].porcentajeAsistencia").value(50))
                .andExpect(jsonPath("$[0].porcentajeFalta").value(50))
                .andExpect(jsonPath("$[0].porcentajeFaltaJustificada").value(50))
                .andExpect(jsonPath("$[0].evento.id").value("1"));
    }

    @Test
    void obtenerMetricasDeAsistenciaParaComision_DeberiaRetornar200YUnaLista() throws Exception {
        when(eventoService.obtenerTodosLosEventosGlobales()).thenReturn(List.of(eventoMock));
        when(eventoMapper.aEventoDTO(any(Evento.class))).thenReturn(eventoDTOMock);
        when(metricaService.porcentajeDeTipoDeAsistenciaPorComision(anyString(), anyString(), any(TipoDeAsistencia.class))).thenReturn(33);

        mockMvc.perform(get("/api/metricas/asistencia/comision/comision1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].porcentajeAsistencia").value(33))
                .andExpect(jsonPath("$[0].porcentajeFalta").value(33))
                .andExpect(jsonPath("$[0].porcentajeFaltaJustificada").value(33))
                .andExpect(jsonPath("$[0].evento.id").value("1"));
    }

    // - CASOS NEGATIVOS

    @Test
    void obtenerMetricasDeBajaDeUnaComision_CuandoComisionNoExiste_DeberiaRetornar404() throws Exception {
        when(metricaService.cantidadTotalDeEstudiantesDeUnaComision(anyString()))
                .thenThrow(new com.unq.mitvu.exceptions.RecursoNoEncontradoException("999", "No se encontró la COMISION con id: 999"));

        mockMvc.perform(get("/api/metricas/estudiantes/dadosDeBaja/porComision/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerMetricasDeAsistenciaGlobal_CuandoNoHayEventos_DeberiaRetornarListaVacia() throws Exception {
        when(eventoService.obtenerTodosLosEventosGlobales()).thenReturn(List.of());

        mockMvc.perform(get("/api/metricas/asistencia")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void obtenerMetricasDeAsistenciaParaComision_CuandoComisionNoExiste_DeberiaRetornar404() throws Exception {
        when(metricaService.porcentajeDeTipoDeAsistenciaPorComision(anyString(), anyString(), any(TipoDeAsistencia.class)))
                .thenThrow(new com.unq.mitvu.exceptions.RecursoNoEncontradoException("999", "No se encontró la COMISION con id: 999"));
        when(eventoService.obtenerTodosLosEventosGlobales()).thenReturn(List.of(eventoMock));
        when(eventoMapper.aEventoDTO(any(Evento.class))).thenReturn(eventoDTOMock);

        mockMvc.perform(get("/api/metricas/asistencia/comision/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
