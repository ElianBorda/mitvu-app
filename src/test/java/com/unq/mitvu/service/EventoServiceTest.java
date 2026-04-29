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

    @Test
    void crear_DeberiaGuardarEnBaseDeDatosYAsignarId() {
        Evento resultado = eventoService.crear(eventoPrueba);

        assertNotNull(resultado);
        assertNotNull(resultado.getId(), "Mongo debería haberle asignado un ID");
        assertEquals("Tutoría de Programación", resultado.getTitulo());

        assertTrue(eventoDAO.findById(resultado.getId()).isPresent());
    }

    @Test
    void crearTodos_DeberiaRetornarListaVaciaSegunImplementacionActual() {
        Evento otroEvento = new Evento();
        otroEvento.setTitulo("Otro evento");
        List<Evento> lista = Arrays.asList(eventoPrueba, otroEvento);

        List<Evento> resultado = eventoService.crearTodos(lista);

        assertTrue(resultado.isEmpty(), "Actualmente el método retorna List.of()");
    }

    @Test
    void obtenerPorId_CuandoExiste_DeberiaRetornarEvento() {
        Evento eventoGuardado = eventoDAO.save(eventoPrueba);
        String idGenerado = eventoGuardado.getId();

        Evento resultado = eventoService.obtenerPorId(idGenerado);

        assertNotNull(resultado);
        assertEquals(idGenerado, resultado.getId());
        assertEquals("Tutoría de Programación", resultado.getTitulo());
    }

    @Test
    void obtenerPorId_CuandoNoExiste_DeberiaLanzarExcepcion() {
        String idFalso = "id-que-no-existe";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.obtenerPorId(idFalso);
        });

        assertEquals("No se encontró el EVENTO con id: " + idFalso, exception.getMessage());
    }

    @Test
    void obtenerTodos_DeberiaRetornarTodosLosEventosGuardados() {
        Evento evento2 = new Evento();
        evento2.setTitulo("Clase de Consulta");
        eventoDAO.save(eventoPrueba);
        eventoDAO.save(evento2);

        List<Evento> resultado = eventoService.obtenerTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    void modificarPorId_DeberiaActualizarLosDatosEnLaBD() {
        Evento eventoGuardado = eventoDAO.save(eventoPrueba);
        String id = eventoGuardado.getId();

        Evento eventoNuevosDatos = new Evento();
        eventoNuevosDatos.setTitulo("Título Modificado");
        eventoNuevosDatos.setDescripcion("Descripción Modificada");

        Evento resultado = eventoService.modificarPorId(id, eventoNuevosDatos);

        assertEquals("Título Modificado", resultado.getTitulo());
        assertEquals("Descripción Modificada", resultado.getDescripcion());

        Evento eventoEnBD = eventoDAO.findById(id).orElseThrow();
        assertEquals("Título Modificado", eventoEnBD.getTitulo());
    }

    @Test
    void eliminarPorId_DeberiaBorrarElEventoDeLaBD() {
        Evento eventoGuardado = eventoDAO.save(eventoPrueba);
        String id = eventoGuardado.getId();
        assertTrue(eventoDAO.existsById(id));

        eventoService.eliminarPorId(id);

        assertFalse(eventoDAO.existsById(id), "El evento no debería existir más en la BD");
    }

    @Test
    void eliminarTodo_DeberiaVaciarLaColeccion() {
        eventoDAO.save(eventoPrueba);
        eventoDAO.save(new Evento());
        assertEquals(2, eventoDAO.count()); // Confirmamos que hay 2

        eventoService.eliminarTodo();

        assertEquals(0, eventoDAO.count(), "La base de datos debería estar vacía");
    }

}