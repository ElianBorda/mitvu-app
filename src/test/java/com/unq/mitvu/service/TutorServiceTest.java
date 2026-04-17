package com.unq.mitvu.service;

import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Tutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TutorServiceTest {

    @Autowired
    public TutorService tutorService;

    @Autowired
    public TutorDAO tutorDAO;

    private Tutor tutor;

    @BeforeEach
    public void setUp() {
        tutor = new Tutor("Juan");
        Tutor tutor2 =  new Tutor("Domingo");

        tutorService.crear(tutor);
        tutorService.crear(tutor2);
    }

    @AfterEach
    public void tearDown() {
        tutorDAO.deleteAll();
    }

    @Test
    public void crearTutor() {
        Tutor tutor = new Tutor("Tutor N");
        Tutor tutorGuardado = tutorService.crear(tutor);

        assertNotNull(tutorGuardado.getId());
        assertEquals(tutor.getNombre(), tutorGuardado.getNombre());
    }

    @Test
    public void testCrearTodosLosTutores() {
        tutorDAO.deleteAll();
        List<Tutor> tutores = new ArrayList<>();
        tutores.add(new Tutor("tutor A"));
        tutores.add(new Tutor("tutor B"));

        tutorService.crearTodos(tutores);

        assertEquals(2, tutorDAO.count());
    }

    @Test
    public void testObtenerTutorPorId() {
        Tutor tutorEncontrado = tutorService.obtenerPorId(tutor.getId());

        assertNotNull(tutorEncontrado);
        assertEquals(tutor.getId(), tutorEncontrado.getId());
        assertEquals("Juan", tutorEncontrado.getNombre());
    }

    @Test
    public void testObtenerTodosLosTutores() {
        List<Tutor> tutores = tutorService.obtenerTodos();
        assertEquals(2, tutores.size());
        assertEquals(tutores.get(0).getNombre(), tutor.getNombre());
    }

    @Test
    public void testModificarTutorPorId() {
        Tutor tutorModificado = new Tutor("tutor modificado");
        Tutor tutorActualizado = tutorService.modificarPorId(tutor.getId(), tutorModificado);

        assertEquals(tutor.getId(), tutorActualizado.getId());
        assertEquals("tutor modificado", tutorActualizado.getNombre());
    }

    @Test
    public void testEliminarTutorPorId() {
        tutorService.eliminarPorId(tutor.getId());
        assertNull(tutorDAO.findById(tutor.getId()).orElse(null));
    }

    @Test
    public void testEliminarTodosLostutors() {
        tutorService.eliminarTodo();
        assertEquals(0, tutorDAO.count());
    }



}
