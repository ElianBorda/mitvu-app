package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TutorServiceTest {
    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorDAO tutorDAO;

    @Autowired
    private ComisionDAO comisionDAO;

    private Tutor tutorPrueba;
    private Comision comisionPrueba;

    @BeforeEach
    void setUp() {
        comisionDAO.deleteAll();
        tutorDAO.deleteAll();

        tutorPrueba = new Tutor("Gomez", "Ana", "11223344", "ana@unq.edu.ar", "pass");
        tutorPrueba = tutorDAO.save(tutorPrueba);

        comisionPrueba = new Comision(
                "Bernal", "Quilmes", "Programación", "Aula 1",
                new Horario(8, 0), new Horario(8,0), DiaHabil.LUNES
        );
        comisionPrueba.setTutor(tutorPrueba);
        comisionPrueba = comisionDAO.save(comisionPrueba);
    }

    @AfterEach
    void tearDown() {
        comisionDAO.deleteAll();
        tutorDAO.deleteAll();
    }

    @Test
    void crear_GuardaElTutorYGeneraId() {
        Tutor nuevoTutor = new Tutor("Perez", "Juan", "40111222", "juan@unq.edu.ar");

        Tutor resultado = tutorService.crear(nuevoTutor);

        assertNotNull(resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertTrue(tutorDAO.existsById(resultado.getId()));
    }

    @Test
    void obtenerPorId_SiExiste_RetornaElTutor() {
        String idTutor = tutorPrueba.getId();

        Tutor resultado = tutorService.obtenerPorId(idTutor);

        assertNotNull(resultado);
        assertEquals("Ana", resultado.getNombre());
        assertEquals("11223344", resultado.getDni());
    }

    @Test
    void obtenerPorId_SiNoExiste_LanzaRecursoNoEncontradoException() {
        assertThrows(RecursoNoEncontradoException.class, () -> {
            tutorService.obtenerPorId("ID-FALSO-TUTOR");
        });
    }

    @Test
    void modificarPorId_ActualizaLosDatosCorrectamente() {
        Tutor datosNuevos = new Tutor("Gomez-Perez", "Ana", "11223344", "ana.nueva@unq.edu.ar");
        String idTutor = tutorPrueba.getId();
        Tutor resultado = tutorService.modificarPorId(idTutor, datosNuevos);

        assertEquals("Gomez-Perez", resultado.getApellido());
        assertEquals("ana.nueva@unq.edu.ar", resultado.getMail());

        Tutor tutorEnBD = tutorDAO.findById(idTutor).get();
        assertEquals("Gomez-Perez", tutorEnBD.getApellido());
    }

    @Test
    void eliminarPorId_DesvinculaSusComisionesYBorraElTutor() {
        String idTutor = tutorPrueba.getId();
        String idComision = comisionPrueba.getId();

        Comision comisionAntes = comisionDAO.findById(idComision).get();
        assertNotNull(comisionAntes.getTutor());
        assertEquals(idTutor, comisionAntes.getTutor().getId());

        tutorService.eliminarPorId(idTutor);

        assertFalse(tutorDAO.existsById(idTutor));

        assertTrue(comisionDAO.existsById(idComision));

        Comision comisionDespues = comisionDAO.findById(idComision).get();
        assertTrue(
                comisionDespues.getTutor() == null || comisionDespues.getTutor().getId() == null,
                "El tutor debería haber sido removido (debe ser null o un proxy vacío)"
        );
    }

    @Test
    void obtenerTodos_RetornaTodosLosTutores() {
        Tutor otroTutor = new Tutor("Martinez", "Carlos", "33445566", "carlos@unq.edu.ar");
        tutorDAO.save(otroTutor);

        List<Tutor> tutores = tutorService.obtenerTodos();

        assertEquals(2, tutores.size());
    }
}
