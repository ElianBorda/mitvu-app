package com.unq.mitvu.service;

import com.unq.mitvu.dao.EventoDAO;
import com.unq.mitvu.model.Evento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class EventoServiceTest {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoDAO eventoDAO;

    private Evento eventoPrueba;

    @BeforeEach
    void setUp() {
        eventoDAO.deleteAll();
        eventoPrueba = new Evento();
        eventoPrueba.setTitulo("Tutoría de Programación");
        eventoPrueba.setDescripcion("Repaso para el parcial");
        eventoPrueba.setFecha(LocalDateTime.of(2026, 4, 28, 15, 30));
    }

    @AfterEach
    void tearDown() {
        eventoDAO.deleteAll();
    }


}