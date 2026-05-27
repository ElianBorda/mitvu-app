package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.ComisionBodyDTO;
import com.unq.mitvu.controller.dto.detalle.ComisionDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.ComisionResumenDTO;
import com.unq.mitvu.mapper.ComisionMapper;
import com.unq.mitvu.mapper.EstudianteMapper;
import com.unq.mitvu.mapper.TutorMapper;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.EstudianteService;
import com.unq.mitvu.service.TutorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComisionController.class)
@AutoConfigureMockMvc(addFilters = false)
class ComisionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TutorService tutorService;

    @MockitoBean
    private ComisionService comisionService;

    @MockitoBean
    private EstudianteService estudianteService;

    @MockitoBean
    private TutorMapper tutorMapper;

    @MockitoBean
    private ComisionMapper comisionMapper;

    @MockitoBean
    private EstudianteMapper estudianteMapper;

    private Comision comisionMock;
    private ComisionResumenDTO comisionResumenDTOMock;
    private ComisionDetalleDTO comisionDetalleDTOMock;
    private ComisionBodyDTO comisionBodyDTO;

    @BeforeEach
    void setUp() {
        comisionMock = new Comision();
        comisionMock.setId("123");

        comisionResumenDTOMock = new ComisionResumenDTO();
        comisionResumenDTOMock.setId("123");

        comisionDetalleDTOMock = new ComisionDetalleDTO();
        comisionDetalleDTOMock.setId("123");

        comisionBodyDTO = new ComisionBodyDTO();
        comisionBodyDTO.setLocalidad("Quilmes");
        comisionBodyDTO.setDepartamento("Informatica");
        comisionBodyDTO.setHorarioInicio("18:00");
        comisionBodyDTO.setHorarioFin("22:00");

        when(estudianteService.obtenerEstudiantesDeComision(anyString())).thenReturn(List.of());
        when(estudianteMapper.aListaDeEstudianteResumenDTO(any())).thenReturn(List.of());
    }

    @Test
    void obtenerComisiones_DeberiaRetornar200YUnaLista() throws Exception {
        when(comisionService.obtenerTodos()).thenReturn(List.of(comisionMock));
        when(comisionMapper.aComisionDetalleDTO(any(Comision.class))).thenReturn(comisionDetalleDTOMock);

        mockMvc.perform(get("/api/comisiones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void obtenerComision_DeberiaRetornar200() throws Exception {
        when(comisionService.obtenerPorId(anyString())).thenReturn(comisionMock);
        when(comisionMapper.aComisionDetalleDTO(any(Comision.class))).thenReturn(comisionDetalleDTOMock);

        mockMvc.perform(get("/api/comisiones/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void crearComision_DeberiaRetornar201() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(comisionBodyDTO);

        when(comisionMapper.aComision(any(ComisionBodyDTO.class))).thenReturn(comisionMock);
        when(comisionService.crear(any(Comision.class))).thenReturn(comisionMock);
        when(comisionMapper.aComisionResumenDTO(any(Comision.class))).thenReturn(comisionResumenDTOMock);

        mockMvc.perform(post("/api/comisiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void eliminarComision_DeberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/comisiones/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void acutualizarComision_DeberiaRetornar200() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(comisionBodyDTO);

        when(comisionMapper.aComision(any(ComisionBodyDTO.class))).thenReturn(comisionMock);
        when(comisionService.modificarPorId(anyString(), any(Comision.class))).thenReturn(comisionMock);
        when(comisionMapper.aComisionDetalleDTO(any(Comision.class))).thenReturn(comisionDetalleDTOMock);

        mockMvc.perform(put("/api/comisiones/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void agregarTutorAComision_DeberiaRetornar200() throws Exception {
        when(comisionService.agregarTutorAComision(anyString(), anyString())).thenReturn(comisionMock);
        when(comisionMapper.aComisionDetalleDTO(any(Comision.class))).thenReturn(comisionDetalleDTOMock);

        mockMvc.perform(put("/api/comisiones/agregarTutor/tutor1/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    // - CASOS NEGATIVOS

    @Test
    void crearComision_SinCamposObligatorios_DeberiaRetornar400() throws Exception {
        ComisionBodyDTO bodyVacio = new ComisionBodyDTO();
        String jsonBody = objectMapper.writeValueAsString(bodyVacio);

        mockMvc.perform(post("/api/comisiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearComision_ConFormatoDeHorarioInvalido_DeberiaRetornar400() throws Exception {
        ComisionBodyDTO bodyHorarioInvalido = new ComisionBodyDTO();
        bodyHorarioInvalido.setLocalidad("Quilmes");
        bodyHorarioInvalido.setDepartamento("Informatica");
        bodyHorarioInvalido.setHorarioInicio("25:99");
        bodyHorarioInvalido.setHorarioFin("8am");
        String jsonBody = objectMapper.writeValueAsString(bodyHorarioInvalido);

        mockMvc.perform(post("/api/comisiones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerComision_CuandoNoExiste_DeberiaRetornar404() throws Exception {
        when(comisionService.obtenerPorId(anyString()))
                .thenThrow(new com.unq.mitvu.exceptions.RecursoNoEncontradoException("999", "No se encontró la COMISIÓN con id: 999"));

        mockMvc.perform(get("/api/comisiones/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarTutorAComision_CuandoYaTieneUno_DeberiaRetornar400() throws Exception {
        when(comisionService.agregarTutorAComision(anyString(), anyString()))
                .thenThrow(new com.unq.mitvu.exceptions.ReglaDeNegocioException("La COMISION ya tiene un TUTOR asignado."));

        mockMvc.perform(put("/api/comisiones/agregarTutor/tutor1/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
