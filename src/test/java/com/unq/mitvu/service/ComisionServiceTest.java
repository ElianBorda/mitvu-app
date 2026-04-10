package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Tutor;
import com.unq.mitvu.model.Turno;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ComisionServiceTest {

    @Autowired
    private ComisionService comisionService;

    @Autowired
    private ComisionDAO comisionDAO;

    @BeforeEach
    public void setUp() {
        comisionDAO.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        comisionDAO.deleteAll();
    }

    @Test
    public void testCrearComision() {
        Tutor tutor = new Tutor("Perez", "Juan", "12345678", "juan.perez@example.com", new ArrayList<>());
        Comision comision = new Comision("Berazategui", "Licenciatura en Informática", "TPI", "10", "18:00 a 22:00", tutor, "juan.perez@example.com");

        Comision comisionGuardada = comisionService.crear(comision);

        assertNotNull(comisionGuardada.getId
                ());
        assertEquals(1, comisionGuardada.getNumero());
        assertEquals("Berazategui", comisionGuardada.getLocalidad());
        assertEquals("Licenciatura en Informática", comisionGuardada.getDepartamento());
        assertEquals("TPI", comisionGuardada.getCarrera());
        assertEquals("10", comisionGuardada.getAula());
        assertEquals("18:00 a 22:00", comisionGuardada.getHorario());
        assertEquals(Turno.NOCHE, comisionGuardada.getTurno());
        assertEquals(tutor, comisionGuardada.getTutor());
        assertEquals("juan.perez@example.com", comisionGuardada.getMailTutor());
        assertEquals(0, comisionGuardada.getAlumnosDni().size());
    }

    @Test
    public void testCrearComisionConMismaLocalidadDepartamentoYCarrera() {
        Tutor tutor = new Tutor("Perez", "Juan", "12345678", "juan.perez@example.com", new ArrayList<>());
        Comision comision1 = new Comision("Berazategui", "Licenciatura en Informática", "TPI", "10", "18:00 a 22:00", tutor, "juan.perez@example.co");
        comisionService.crear(comision1);

        Comision comision2 = new Comision("Berazategui", "Licenciatura en Informática", "TPI", "11", "18:00 a 22:00", tutor, "juan.perez@example.com");
        Comision comisionGuardada = comisionService.crear(comision2);

        assertEquals(2, comisionGuardada.getNumero());
    }
}
