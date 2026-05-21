package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.BajaEstudianteBodyDTO;
import com.unq.mitvu.controller.body.EstudianteBodyDTO;
import com.unq.mitvu.controller.dto.AsistenciaDTO;
import com.unq.mitvu.controller.dto.detalle.EstudianteDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.EstudianteResumenDTO;
import com.unq.mitvu.mapper.ComisionMapper;
import com.unq.mitvu.mapper.EstudianteMapper;
import com.unq.mitvu.mapper.TutorMapper;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.MotivoBaja;
import com.unq.mitvu.model.Rol;
import com.unq.mitvu.model.TipoDeAsistencia;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.EstudianteService;
import com.unq.mitvu.service.TutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstudianteController.class)
@AutoConfigureMockMvc(addFilters = false)
class EstudianteControllerTest {

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

    @MockitoBean
    private RabbitTemplate rabbitTemplate;

    private Estudiante estudianteMock;
    private EstudianteDetalleDTO estudianteDetalleDTOMock;
    private EstudianteResumenDTO estudianteResumenDTOMock;
    private EstudianteBodyDTO estudianteBodyDTO;
    private BajaEstudianteBodyDTO bajaEstudianteBodyDTO;

    @BeforeEach
    void setUp() {
        estudianteMock = new Estudiante("Gomez", "Juan", "12345678", "juan@mail.com", "pass", Rol.ESTUDIANTE, "TPI");
        estudianteMock.setId("123");

        estudianteDetalleDTOMock = new EstudianteDetalleDTO();
        estudianteDetalleDTOMock.setId("123");

        estudianteResumenDTOMock = new EstudianteResumenDTO();
        estudianteResumenDTOMock.setId("123");

        bajaEstudianteBodyDTO = new BajaEstudianteBodyDTO();
        bajaEstudianteBodyDTO.setMotivo(MotivoBaja.OTRO);
        bajaEstudianteBodyDTO.setDetalle("Se superpone con el trabajo");

        estudianteBodyDTO = new EstudianteBodyDTO();
        estudianteBodyDTO.setNombre("Juan");
        estudianteBodyDTO.setApellido("Gomez");
        estudianteBodyDTO.setDni("12345678");
        estudianteBodyDTO.setMail("juan@mail.com");
        estudianteBodyDTO.setCarrera("TPI");

        when(estudianteMapper.aEstudianteDetalleDTO(any(Estudiante.class))).thenReturn(estudianteDetalleDTOMock);
        when(estudianteMapper.aBajaEstudianteDTO(any())).thenReturn(null);
        when(comisionMapper.aComisionParaEstudianteDTO(any())).thenReturn(null);
    }

    @Test
    void obtenerEstudiantes_DeberiaRetornar200() throws Exception {
        when(estudianteService.obtenerTodos()).thenReturn(List.of(estudianteMock));

        mockMvc.perform(get("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void obtenerEstudiante_DeberiaRetornar200() throws Exception {
        when(estudianteService.obtenerPorId(anyString())).thenReturn(estudianteMock);

        mockMvc.perform(get("/api/estudiantes/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void crearEstudiante_DeberiaRetornar201() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(estudianteBodyDTO);

        when(estudianteMapper.aEstudiante(any(EstudianteBodyDTO.class))).thenReturn(estudianteMock);
        when(estudianteService.crear(any(Estudiante.class))).thenReturn(estudianteMock);
        when(estudianteMapper.aEstudianteResumenDTO(any(Estudiante.class))).thenReturn(estudianteResumenDTOMock);

        mockMvc.perform(post("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void crearEstudiantes_DeberiaRetornar200() throws Exception {
        List<EstudianteBodyDTO> listaBody = List.of(estudianteBodyDTO);
        String jsonBody = objectMapper.writeValueAsString(listaBody);

        when(estudianteMapper.aEstudiante(any(EstudianteBodyDTO.class))).thenReturn(estudianteMock);
        when(estudianteMapper.aListaDeEstudianteResumenDTO(any())).thenReturn(List.of(estudianteResumenDTOMock));

        mockMvc.perform(post("/api/estudiantes/varios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void eliminarEstudiante_DeberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/estudiantes/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void asignarEstudianteAComision_DeberiaRetornar200() throws Exception {
        when(estudianteService.agregarEstudianteAComision(anyString(), anyString())).thenReturn(estudianteMock);

        mockMvc.perform(put("/api/estudiantes/123/asignarComision/comision1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void obtenerEstudiantesDeComision_DeberiaRetornar200() throws Exception {
        when(estudianteService.obtenerEstudiantesDeComision(anyString())).thenReturn(List.of(estudianteMock));

        mockMvc.perform(get("/api/estudiantes/comision/comision1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void darDeBajaAEstudianteDeComision_DeberiaRetornar200() throws Exception {
        bajaEstudianteBodyDTO.setMotivo(MotivoBaja.OTRO);
        bajaEstudianteBodyDTO.setDetalle("Detalles de prueba");
        String jsonBody = objectMapper.writeValueAsString(bajaEstudianteBodyDTO);

        when(estudianteMapper.aFormularioBaja(any(BajaEstudianteBodyDTO.class))).thenReturn(null); // Con null basta para el test

        when(estudianteService.darseDeBaja(anyString(), any())).thenReturn(estudianteMock);

        mockMvc.perform(put("/api/estudiantes/123/baja")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void pasarAsistenciaDeEstudiante_DeberiaRetornar200() throws Exception {
        AsistenciaDTO asistenciaDTO = new AsistenciaDTO(LocalDate.now(), TipoDeAsistencia.PRESENTE, "Prueba");
        String jsonBody = objectMapper.writeValueAsString(asistenciaDTO);

        when(estudianteService.pasarAsistenciaDeEstudiante(anyString(), any())).thenReturn(estudianteMock);

        mockMvc.perform(put("/api/estudiantes/123/pasarAsistencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void estaDadoDeBaja_DeberiaRetornar200() throws Exception {
        when(estudianteService.estaDadoDeBaja(anyString())).thenReturn(true);

        mockMvc.perform(get("/api/estudiantes/123/dadoDeBaja")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void obtenerEstudiantesDeBaja_DeberiaRetornar200() throws Exception {
        when(estudianteService.obtenerEstudiantesDeBaja()).thenReturn(List.of(estudianteMock));

        mockMvc.perform(get("/api/estudiantes/baja")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void obtenerEstudiantesActivos_DeberiaRetornar200() throws Exception {
        when(estudianteService.obtenerEstudiantesActivos()).thenReturn(List.of(estudianteMock));

        mockMvc.perform(get("/api/estudiantes/activos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void obtenerTodosLosEstudiantesDadosDeBajaDeUnaComision_DeberiaRetornar200() throws Exception {
        when(estudianteService.obtenerTodosLosEstudiantesDadosDeBajaDeUnaComision(anyString())).thenReturn(List.of(estudianteMock));
        when(estudianteMapper.aListaDeEstudianteResumenDTO(any())).thenReturn(List.of(estudianteResumenDTOMock));

        mockMvc.perform(get("/api/estudiantes/comision/comision1/baja")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
