package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Horario;
import com.unq.mitvu.model.Tutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EstudianteServiceTest {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private EstudianteDAO estudianteDAO;

    @Autowired
    private ComisionDAO comisionDAO;

    private Estudiante estudiante;
    private Comision comision;

    @BeforeEach
    public void setUp() {
        Horario horarioInicio = new Horario(8, 0);
        Horario horarioFin = new Horario(11, 0);
        comision = new Comision(
                new Tutor(),
                horarioFin,
                horarioInicio,
                "37B",
                "Lic. en Informática",
                "CyT",
                "Bernal",
                1
        );
        comisionDAO.save(comision);

        estudiante = new Estudiante("Perez", "Nacho", "12345678", "TPI", comision, 0);
        Estudiante estudiante2 = new Estudiante("Gomez", "Elian", "87654321", "TPI", comision, 0);

        estudianteService.crear(estudiante);
        estudianteService.crear(estudiante2);
    }

    @AfterEach
    public void tearDown() {
        estudianteDAO.deleteAll();
        comisionDAO.deleteAll();
    }

    @Test
    public void testCrearEstudiante() {
        Estudiante nuevoEstudiante = new Estudiante("Lopez", "estudiante 3", "11223344", "TPI", comision, 0);
        Estudiante estudianteGuardado = estudianteService.crear(nuevoEstudiante);

        assertNotNull(estudianteGuardado.getId());
        assertEquals("estudiante 3", estudianteGuardado.getNombre());
    }

    @Test
    public void testCrearTodosLosEstudiantes() {
        estudianteDAO.deleteAll();
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("Garcia", "estudiante A", "22334455", "TPI", comision, 0));
        estudiantes.add(new Estudiante("Martinez", "estudiante B", "33445566", "TPI", comision, 0));

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
        Estudiante estudianteModificado = new Estudiante("Gonzalez", "estudiante modificado", "44556677", "TPI", comision, 1);
        Estudiante estudianteActualizado = estudianteService.modificarPorId(estudiante.getId(), estudianteModificado);

        assertEquals(estudiante.getId(), estudianteActualizado.getId());
        assertEquals("estudiante modificado", estudianteActualizado.getNombre());
        assertEquals("Gonzalez", estudianteActualizado.getApellido());
        assertEquals("44556677", estudianteActualizado.getDni());
        assertEquals(1, estudianteActualizado.getCantidadAsistencias());
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
