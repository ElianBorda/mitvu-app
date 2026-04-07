package com.unq.mitvu.service;

import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.model.Estudiante;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EstudianteServiceTest {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private EstudianteDAO estudianteDAO;

    private Estudiante estudiante;

    @BeforeEach
    public void setUp() {
        estudiante = new Estudiante("Nacho");
        Estudiante estudiante2 = new Estudiante("Elian");

        estudianteService.crear(estudiante);
        estudianteService.crear(estudiante2);
    }

    @AfterEach
    public void tearDown() {
        estudianteDAO.deleteAll();
    }

    @Test
    public void testCrearestudiante() {
        Estudiante nuevaestudiante = new Estudiante("estudiante 3");
        Estudiante estudianteGuardada = estudianteService.crear(nuevaestudiante);

        assertNotNull(estudianteGuardada.getId());
        assertEquals("estudiante 3", estudianteGuardada.getNombre());
    }

    @Test
    public void testCrearTodasLasestudiantes() {
        estudianteDAO.deleteAll();
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("estudiante A"));
        estudiantes.add(new Estudiante("estudiante B"));

        estudianteService.crearTodos(estudiantes);

        assertEquals(2, estudianteDAO.count());
    }

    @Test
    public void testObtenerestudiantePorId() {
        Estudiante estudianteEncontrada = estudianteService.obtenerPorId(estudiante.getId());

        assertNotNull(estudianteEncontrada);
        assertEquals(estudiante.getId(), estudianteEncontrada.getId());
        assertEquals("Nacho", estudianteEncontrada.getNombre());
    }

    @Test
    public void testModificarestudiantePorId() {
        Estudiante estudianteModificada = new Estudiante("estudiante Modificada");
        Estudiante estudianteActualizada = estudianteService.modificarPorId(estudiante.getId(), estudianteModificada);

        assertEquals(estudiante.getId(), estudianteActualizada.getId());
        assertEquals("estudiante Modificada", estudianteActualizada.getNombre());
    }

    @Test
    public void testEliminarestudiantePorId() {
        estudianteService.eliminarPorId(estudiante.getId());
        assertNull(estudianteDAO.findById(estudiante.getId()).orElse(null));
    }

    @Test
    public void testEliminarTodasLasestudiantes() {
        estudianteService.eliminarTodo();
        assertEquals(0, estudianteDAO.count());
    }
    
}
