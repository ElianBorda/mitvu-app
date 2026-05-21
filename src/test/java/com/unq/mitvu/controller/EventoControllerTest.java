package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.EventoBodyDTO;
import com.unq.mitvu.controller.dto.EventoDTO;
import com.unq.mitvu.mapper.EventoMapper;
import com.unq.mitvu.model.Evento;
import com.unq.mitvu.service.EventoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventoController.class)
@AutoConfigureMockMvc(addFilters = false)
class EventoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventoService eventoService;

    @MockitoBean
    private EventoMapper eventoMapper;

    private Evento eventoMock;
    private EventoDTO eventoDTOMock;
    private EventoBodyDTO eventoBodyDTO;

    @BeforeEach
    void setUp() {
        eventoMock = new Evento();
        eventoMock.setId("123");

        eventoDTOMock = new EventoDTO();
        eventoDTOMock.setId("123");

        eventoBodyDTO = new EventoBodyDTO();
        eventoBodyDTO.setTitulo("Título de prueba");
        eventoBodyDTO.setFecha(LocalDate.now());
    }

    @Test
    void crearEventoAdmin_DeberiaRetornar201() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(eventoBodyDTO);

        when(eventoMapper.aEvento(any(EventoBodyDTO.class))).thenReturn(eventoMock);
        when(eventoService.crear(any(Evento.class))).thenReturn(eventoMock);
        when(eventoMapper.aEventoDTO(any(Evento.class))).thenReturn(eventoDTOMock);

        mockMvc.perform(post("/api/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void crearEventoParaComision_DeberiaRetornar201() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(eventoBodyDTO);

        when(eventoMapper.aEvento(any(EventoBodyDTO.class))).thenReturn(eventoMock);
        when(eventoService.crear(any(Evento.class))).thenReturn(eventoMock);
        when(eventoMapper.aEventoDTO(any(Evento.class))).thenReturn(eventoDTOMock);

        mockMvc.perform(post("/api/eventos/comision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void modificarEvento_DeberiaRetornar200() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(eventoBodyDTO);

        when(eventoMapper.aEvento(any(EventoBodyDTO.class))).thenReturn(eventoMock);
        when(eventoService.modificarPorId(anyString(), any(Evento.class))).thenReturn(eventoMock);
        when(eventoMapper.aEventoDTO(any(Evento.class))).thenReturn(eventoDTOMock);

        mockMvc.perform(put("/api/eventos/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    void eliminarEvento_DeberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/eventos/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerEventosGlobales_DeberiaRetornar200() throws Exception {
        when(eventoService.obtenerTodosLosEventosGlobales()).thenReturn(List.of(eventoMock));
        when(eventoMapper.aListaDeEventoDTO(any())).thenReturn(List.of(eventoDTOMock));

        mockMvc.perform(get("/api/eventos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void obtenerEventosDeUnaComision_DeberiaRetornar200() throws Exception {
        when(eventoService.obtenerTodosLosEventosParaComision(anyString())).thenReturn(List.of(eventoMock));
        when(eventoMapper.aListaDeEventoDTO(any())).thenReturn(List.of(eventoDTOMock));

        mockMvc.perform(get("/api/eventos/comision/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void eliminarEventos_DeberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/api/eventos"))
                .andExpect(status().isNoContent());
    }
}
