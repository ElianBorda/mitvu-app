package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.IDsBodyDTO;
import com.unq.mitvu.controller.body.TutorBodyDTO;
import com.unq.mitvu.controller.dto.detalle.TutorDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.TutorResumenDTO;
import com.unq.mitvu.mapper.ComisionMapper;
import com.unq.mitvu.mapper.EstudianteMapper;
import com.unq.mitvu.mapper.TutorMapper;
import com.unq.mitvu.model.Rol;
import com.unq.mitvu.model.Tutor;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TutorController.class)
@AutoConfigureMockMvc(addFilters = false)
class TutorControllerTest {

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

    private Tutor tutorMock;
    private TutorDetalleDTO tutorDetalleDTOMock;
    private TutorResumenDTO tutorResumenDTOMock;
    private TutorBodyDTO tutorBodyDTO;

    @BeforeEach
    void setUp() {
        tutorMock = mock(Tutor.class);
        when(tutorMock.getId()).thenReturn("123");
        when(tutorMock.getRol()).thenReturn(Rol.TUTOR);

        tutorDetalleDTOMock = new TutorDetalleDTO();
        tutorDetalleDTOMock.setId("123");

        tutorResumenDTOMock = new TutorResumenDTO();
        tutorResumenDTOMock.setId("123");

        tutorBodyDTO = new TutorBodyDTO();
        tutorBodyDTO.setNombre("Carlos");
        tutorBodyDTO.setApellido("Lopez");
        tutorBodyDTO.setDni("87654321");
        tutorBodyDTO.setMail("carlos.lopez@mail.com");
        tutorBodyDTO.setComisiones_ids(new ArrayList<>());

        when(comisionService.obtenerComisionesDeTutor(anyString())).thenReturn(List.of());
        when(estudianteService.obtenerTodosLosEstudiantesDeTutor(anyString())).thenReturn(List.of());
        when(tutorMapper.aTutorDetalleDTO(any(Tutor.class))).thenReturn(tutorDetalleDTOMock);
    }

    @Test
    void obtenerTutores_DeberiaRetornar200() throws Exception {
        when(tutorService.obtenerTodos()).thenReturn(List.of(tutorMock));

        mockMvc.perform(get("/api/tutores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void obtenerTutor_DeberiaRetornar200() throws Exception {
        when(tutorService.obtenerPorId(anyString())).thenReturn(tutorMock);

        mockMvc.perform(get("/api/tutores/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void crearTutor_DeberiaRetornar201() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(tutorBodyDTO);

        when(tutorMapper.aTutor(any(TutorBodyDTO.class))).thenReturn(tutorMock);
        when(tutorService.crear(any(Tutor.class))).thenReturn(tutorMock);
        when(tutorMapper.aTutorResumenDTO(any(Tutor.class))).thenReturn(tutorResumenDTOMock);

        mockMvc.perform(post("/api/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void eliminarTutor_DeberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/tutores/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void actualizarTutor_DeberiaRetornar200() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(tutorBodyDTO);

        when(tutorMapper.aTutor(any(TutorBodyDTO.class))).thenReturn(tutorMock);
        when(tutorService.modificarPorId(anyString(), any(Tutor.class))).thenReturn(tutorMock);

        mockMvc.perform(put("/api/tutores/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void agregarTutorAComisionesPorId_DeberiaRetornar200() throws Exception {
        IDsBodyDTO idsBodyDTO = new IDsBodyDTO(List.of("comision1", "comision2"));
        String jsonBody = objectMapper.writeValueAsString(idsBodyDTO);

        when(tutorService.obtenerPorId(anyString())).thenReturn(tutorMock);

        mockMvc.perform(put("/api/tutores/asignarComisionesATutor/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void obtenerTutorDeLaComision_DeberiaRetornar200() throws Exception {
        when(tutorService.obtenerTutorDeLaComision(anyString())).thenReturn(tutorMock);

        mockMvc.perform(get("/api/tutores/comision/comision1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }
}
