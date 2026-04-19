package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import com.unq.mitvu.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class EstudianteServiceTest {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private EstudianteDAO estudianteDAO;

    @Autowired
    private ComisionDAO comisionDAO;

    @Autowired
    private TutorDAO tutorDAO;

    private Estudiante estudiantePrueba;
    private Comision comisionPrueba;
    private Tutor tutorPrueba;


    @BeforeEach
    void setUp() {
        // 1. Limpieza de la base de datos
        estudianteDAO.deleteAll();
        comisionDAO.deleteAll();
        tutorDAO.deleteAll();

        // 2. Creación de Tutor
        tutorPrueba = new Tutor("Gomez", "Ana", "11223344", "ana@unq.edu.ar", "pass");
        tutorPrueba = tutorDAO.save(tutorPrueba);

        // 3. Creación de Comisión (y le asignamos el tutor)
        comisionPrueba = new Comision(
                "Bernal", "Quilmes", "Programación", "Aula 5",
                new Horario(8, 0), new Horario(9, 0), DiaHabil.MIERCOLES
        );

        comisionPrueba.setTutor(tutorPrueba);
        comisionPrueba = comisionDAO.save(comisionPrueba);


        // 4. Creación de Estudiante (Sin comisión asignada inicialmente)
        estudiantePrueba = new Estudiante();
        // Suponiendo que tienes estos setters, si no, usa el constructor correspondiente
        estudiantePrueba.setNombre("Luciano");
        estudiantePrueba.setApellido("Perez");
        estudiantePrueba.setDni("40111222");
        estudiantePrueba.setCarrera("TPI");

        estudiantePrueba = estudianteDAO.save(estudiantePrueba);
    }

    @AfterEach
    void tearDown() {
        estudianteDAO.deleteAll();
        comisionDAO.deleteAll();
        tutorDAO.deleteAll();
    }

    @Test
    void crear_GuardaElEstudianteYGeneraId() {
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setNombre("Maria");
        nuevoEstudiante.setDni("41000000");

        Estudiante resultado = estudianteService.crear(nuevoEstudiante);

        assertNotNull(resultado.getId());
        assertEquals("Maria", resultado.getNombre());
        assertTrue(estudianteDAO.existsById(resultado.getId()));
    }

    @Test
    void obtenerPorId_SiNoExiste_LanzaRecursoNoEncontradoException() {
        assertThrows(RecursoNoEncontradoException.class, () -> {
            estudianteService.obtenerPorId("ID-FALSO");
        });
    }

    @Test
    void agregarEstudianteAComision_AsignaCorrectamente() {
        String idEstudiante = estudiantePrueba.getId();
        String idComision = comisionPrueba.getId();

        Estudiante resultado = estudianteService.agregarEstudianteAComision(idEstudiante, idComision);

        assertNotNull(resultado.getComision());
        assertEquals(idComision, resultado.getComision().getId());

        Estudiante estudianteEnBD = estudianteDAO.findById(idEstudiante).get();
        assertEquals(idComision, estudianteEnBD.getComision().getId());
    }

    @Test
    void agregarEstudianteAComision_SiYaTieneComision_LanzaReglaDeNegocioException() {
        estudiantePrueba.setComision(comisionPrueba);
        estudianteDAO.save(estudiantePrueba);

        String idEstudiante = estudiantePrueba.getId();
        String idComision = comisionPrueba.getId();

        ReglaDeNegocioException excepcion = assertThrows(ReglaDeNegocioException.class, () -> {
            estudianteService.agregarEstudianteAComision(idEstudiante, idComision);
        });

        assertTrue(excepcion.getMessage().contains("ya se encuentra en la COMISION"));
    }

    @Test
    void cambiarEstudianteDeComision_ActualizaLaComisionDelAlumno() {
        estudiantePrueba.setComision(comisionPrueba);
        estudianteDAO.save(estudiantePrueba);

        Comision nuevaComision = new Comision("Wilde", "Avellaneda", "Redes", "Aula 2", new Horario(8,0), new Horario(9,0), DiaHabil.JUEVES);
        nuevaComision = comisionDAO.save(nuevaComision);

        Estudiante resultado = estudianteService.cambiarEstudianteDeComision(estudiantePrueba.getId(), nuevaComision.getId());

        assertEquals(nuevaComision.getId(), resultado.getComision().getId());
    }

    @Test
    void eliminarComisionDeEstudiante_DejaAlEstudianteSinAsignar() {
        estudiantePrueba.setComision(comisionPrueba);
        estudianteDAO.save(estudiantePrueba);

        Estudiante resultado = estudianteService.eliminarComisionDeEstudiante(estudiantePrueba.getId());

        assertNull(resultado.getComision());
    }

    @Test
    void obtenerTodosLosEstudiantesDeTutor_RetornaListaCorrecta() {
        estudiantePrueba.setComision(comisionPrueba);
        estudianteDAO.save(estudiantePrueba);

        Estudiante estudianteSuelto = new Estudiante();
        estudianteDAO.save(estudianteSuelto);

        List<Estudiante> estudiantesDelTutor = estudianteService.obtenerTodosLosEstudiantesDeTutor(tutorPrueba.getId());

        assertEquals(1, estudiantesDelTutor.size());
        assertEquals(estudiantePrueba.getId(), estudiantesDelTutor.get(0).getId());
    }
}

