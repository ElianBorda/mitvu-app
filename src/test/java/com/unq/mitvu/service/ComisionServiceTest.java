package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Horario;
import com.unq.mitvu.model.Tutor;
import com.unq.mitvu.model.Turno;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ComisionServiceTest {

    @Autowired
    private ComisionService comisionService;

    @Autowired
    private ComisionDAO comisionDAO;

    @Autowired TutorService tutorService;

    private Comision comision;
    private Tutor tutor;
    private Horario horarioInicio;
    private Horario horarioFin;


    @BeforeEach
    public void setUp() {
        tutor = tutorService.crear(
                new Tutor(
                "Borda",
                "Elian",
                "44862090",
                    "elian@gmail.com"
        ));

        horarioInicio = new Horario(8, 0);
        horarioFin = new Horario(11, 0);
        comision = new Comision(
                tutor,
                horarioFin,
                horarioInicio,
                "37B",
                "Lic. en Informática",
                "CyT",
                "Bernal",
                1
        );
    }

    @AfterEach
    public void tearDown() {
        comisionDAO.deleteAll();
        tutorService.eliminarTodo();
    }

    @Test
    public void testCrearComision() {
        Comision comisionGuardada = comisionService.crear(comision);
        assertNotNull(comisionGuardada);
        assertEquals(1, comisionGuardada.getNumero());
        assertEquals(Turno.MANANA, comisionGuardada.getTurno());
        assertEquals(tutor.getId(), comisionGuardada.getTutor().getId());
        assertEquals(horarioInicio.toString(), comisionGuardada.getHorarioInicio().toString());
        assertEquals(horarioFin.toString(), comisionGuardada.getHorarioFin().toString());
        assertEquals("37B", comisionGuardada.getAula());
        assertEquals("Lic. en Informática", comisionGuardada.getCarrera());
        assertEquals("Bernal", comisionGuardada.getLocalidad());
        assertEquals("CyT", comisionGuardada.getDepartamento());
        assertNotNull(comisionGuardada.getId());
    }

    @Test void testObtenerPorId() {
        Comision comisionGuardada = comisionService.crear(comision);
        Comision comisionObtenida = comisionService.obtenerPorId(comisionGuardada.getId());
        assertNotNull(comisionObtenida);
        assertEquals(1, comisionObtenida.getNumero());
        assertEquals(Turno.MANANA, comisionObtenida.getTurno());
        assertEquals(tutor.getId(), comisionObtenida.getTutor().getId());
        assertEquals(horarioInicio.toString(), comisionObtenida.getHorarioInicio().toString());
        assertEquals(horarioFin.toString(), comisionObtenida.getHorarioFin().toString());
        assertEquals("37B", comisionObtenida.getAula());
        assertEquals("Lic. en Informática", comisionObtenida.getCarrera());
        assertEquals("Bernal", comisionObtenida.getLocalidad());
        assertEquals("CyT", comisionObtenida.getDepartamento());
        assertNotNull(comisionObtenida.getId());
    }

    @Test void testModificarPorId() {
        Comision comisionGuardada = comisionService.crear(comision);
        Comision comisionModificada = new Comision(
                tutor,
                horarioFin,
                horarioInicio,
                "37B",
                "Lic. en Informática",
                "CyT",
                "Bernal",
                2
        );
        comisionModificada = comisionService.modificarPorId(comisionGuardada.getId(), comisionModificada);
        assertNotNull(comisionModificada);
        assertEquals(2, comisionModificada.getNumero());
        assertEquals(Turno.MANANA, comisionModificada.getTurno());
        assertEquals(tutor.getId(), comisionModificada.getTutor().getId());
        assertEquals(horarioInicio.toString(), comisionModificada.getHorarioInicio().toString());
        assertEquals(horarioFin.toString(), comisionModificada.getHorarioFin().toString());
        assertEquals("37B", comisionModificada.getAula());
        assertEquals("Lic. en Informática", comisionModificada.getCarrera());
        assertEquals("Bernal", comisionModificada.getLocalidad());
        assertEquals("CyT", comisionModificada.getDepartamento());
        assertNotNull(comisionModificada.getId());
    }

    @Test
    public void testEliminarPorId() {
        Comision comisionGuardada = comisionService.crear(comision);
        comisionService.eliminarPorId(comisionGuardada.getId());
        Comision comisionEliminada = comisionService.obtenerPorId(comisionGuardada.getId());
        assertNull(comisionEliminada);
    }
}
