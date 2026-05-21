package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.AnuncioBodyDTO;
import com.unq.mitvu.controller.dto.AnuncioDTO;
import com.unq.mitvu.mapper.AnuncioMapper;
import com.unq.mitvu.model.Anuncio;
import com.unq.mitvu.service.AnuncioService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnuncioController.class)
@AutoConfigureMockMvc(addFilters = false) //Apagamos Spring Segurity
class AnuncioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AnuncioService anuncioService;

    @MockitoBean
    private AnuncioMapper anuncioMapper;

    private Anuncio anuncioMock;
    private AnuncioDTO anuncioDTOMock;

    @BeforeEach
    void setUp() {
        anuncioMock = new Anuncio();

        anuncioDTOMock = new AnuncioDTO();
    }

    @Test
    void crearAnuncio_DeberiaRetornar201YElAnuncioCreado() throws Exception {
        AnuncioBodyDTO bodyDTO = new AnuncioBodyDTO();

        bodyDTO.setTitulo("Título de prueba válido");
        String jsonBody = objectMapper.writeValueAsString(bodyDTO);

        when(anuncioMapper.aAnuncio(any(AnuncioBodyDTO.class))).thenReturn(anuncioMock);
        when(anuncioService.crear(any(Anuncio.class))).thenReturn(anuncioMock);
        when(anuncioMapper.aAnuncioDTO(any(Anuncio.class))).thenReturn(anuncioDTOMock);

        mockMvc.perform(post("/api/anuncios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Test
    void obtenerTodos_DeberiaRetornar200YUnaLista() throws Exception {
        List<Anuncio> anunciosMock = List.of(anuncioMock);
        List<AnuncioDTO> listaDtoMock = List.of(anuncioDTOMock);

        when(anuncioService.obtenerTodos()).thenReturn(anunciosMock);
        when(anuncioMapper.aListaDeAnuncioDTO(anunciosMock)).thenReturn(listaDtoMock);

        mockMvc.perform(get("/api/anuncios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}