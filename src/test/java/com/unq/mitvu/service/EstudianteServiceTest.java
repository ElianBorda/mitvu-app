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
    public void testCrearEstudiante() {
        Estudiante nuevoEstudiante = new Estudiante("estudiante 3");
        Estudiante estudianteGuardado = estudianteService.crear(nuevoEstudiante);

        assertNotNull(estudianteGuardado.getId());
        assertEquals("estudiante 3", estudianteGuardado.getNombre());
    }

    @Test
    public void testCrearTodosLosEstudiantes() {
        estudianteDAO.deleteAll();
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("estudiante A"));
        estudiantes.add(new Estudiante("estudiante B"));

        estudianteService.crearTodos(estudiantes);

        assertEquals(2, estudianteDAO.count());
    }

    @Test
    public void testObtenerEstudiantePorId() {
        Estudiante estudianteEncontrado = estudianteService.obtenerPorId(estudiante.getId());

        assertNotNull(estudianteEncontrado);
        assertEquals(estudiante.getId(), estudianteEncontrado.getId());
        assertEquals("Nacho", estudianteEncontrado.getNombre());
    }

    @Test
    public void testObtenerTodosLosEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        assertEquals(2, estudiantes.size());
        assertEquals(estudiantes.get(0).getNombre(), estudiante.getNombre());
    }

    @Test
    public void testModificarEstudiantePorId() {
        Estudiante estudianteModificado = new Estudiante("estudiante modificado");
        Estudiante estudianteActualizado = estudianteService.modificarPorId(estudiante.getId(), estudianteModificado);

        assertEquals(estudiante.getId(), estudianteActualizado.getId());
        assertEquals("estudiante modificado", estudianteActualizado.getNombre());
    }

    @Test
    public void testEliminarEstudiantePorId() {
        estudianteService.eliminarPorId(estudiante.getId());
        assertNull(estudianteDAO.findById(estudiante.getId()).orElse(null));
    }

    @Test
    public void testEliminarTodosLosEstudiantes() {
        estudianteService.eliminarTodo();
        assertEquals(0, estudianteDAO.count());
    }
    
}
