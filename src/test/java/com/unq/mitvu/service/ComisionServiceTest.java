package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import com.unq.mitvu.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ComisionServiceTest {

    @Autowired
    private ComisionService comisionService;

    @Autowired
    private ComisionDAO comisionDAO;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private EstudianteService estudianteService;

    private Comision comisionPrueba;
    private Tutor tutorPrueba;

    @BeforeEach
    void setUp() {
        comisionService.eliminarTodo();
        Horario horarioInicio = new Horario(8, 0);
        Horario horarioFin = new Horario(9, 0);

        comisionPrueba = new Comision(
                "Bernal", "Quilmes", "Programación", "Aula 1",
                horarioInicio, horarioFin, DiaHabil.LUNES
        );

        tutorPrueba = new Tutor("Gomez", "Juan", "12345678", "juan@unq.edu.ar", "password123", Rol.TUTOR);

        tutorPrueba = tutorService.crear(tutorPrueba);
        comisionPrueba = comisionDAO.save(comisionPrueba);
    }

    @AfterEach
    void tearDown() {
        comisionService.eliminarTodo();
        tutorService.eliminarTodo();
        estudianteService.eliminarTodo();
    }

    @Test
    void crear_AsignaNumeroCorrectoYGuardaEnBD() {
        Comision nuevaComision = new Comision(
                "Bernal", "Quilmes", "Programación", "Aula 2",
                new Horario(10, 0), new Horario(11, 0), DiaHabil.MARTES
        );

        Comision resultado = comisionService.crear(nuevaComision);

        assertNotNull(resultado.getId());
        assertEquals(1, resultado.getNumero());
        assertEquals(2, comisionDAO.count());
    }

    @Test
    void obtenerPorId_SiExiste_RetornaLaComisionReal() {
        String idReal = comisionPrueba.getId();

        Comision resultado = comisionService.obtenerPorId(idReal);

        assertNotNull(resultado);
        assertEquals(idReal, resultado.getId());
        assertEquals("Programación", resultado.getCarrera());
    }

    @Test
    void obtenerPorId_SiNoExiste_LanzaExcepcion() {
        String idInvalido = "id-que-no-existe-123";

        assertThrows(RecursoNoEncontradoException.class, () -> {
            comisionService.obtenerPorId(idInvalido);
        });
    }

    @Test
    void agregarTutorAComision_ActualizaLaComisionEnBD() {
        String idTutor = tutorPrueba.getId();
        String idComision = comisionPrueba.getId();

        Comision resultado = comisionService.cambiarDeTutorEnComision(idTutor, idComision);

        assertNotNull(resultado.getTutor());
        assertEquals(idTutor, resultado.getTutor().getId());

        Comision comisionEnBD = comisionDAO.findById(idComision).get();

        assertEquals(idTutor, comisionEnBD.getTutor().getId());
    }

    @Test
    void agregarTutorAComision_SiYaTieneTutor_LanzaExcepcion() {
        comisionPrueba.setTutor(tutorPrueba);
        comisionDAO.save(comisionPrueba);

        String idTutor = tutorPrueba.getId();
        String idComision = comisionPrueba.getId();

        ReglaDeNegocioException excepcion = assertThrows(ReglaDeNegocioException.class, () -> {
            comisionService.agregarTutorAComision(idTutor, idComision);
        });

        assertTrue(excepcion.getMessage().contains("ya tiene un TUTOR"));
    }

    @Test
    void eliminarPorId_BorraFisicamenteLaComision() {
        String idEliminar = comisionPrueba.getId();

        assertTrue(comisionDAO.existsById(idEliminar));

        comisionService.eliminarPorId(idEliminar);

        assertFalse(comisionDAO.existsById(idEliminar));
    }
}
