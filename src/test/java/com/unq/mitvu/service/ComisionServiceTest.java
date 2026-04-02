package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.model.Comision;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ComisionServiceTest {

    @Autowired
    private ComisionService comisionService;

    @Autowired
    private ComisionDAO comisionDAO;

    private Comision comision;

    @BeforeEach
    public void setUp() {
        comision = new Comision("Comision 1");
        Comision comision2 = new Comision("Comision 2");

        comisionService.crear(comision);
        comisionService.crear(comision2);
    }

    @AfterEach
    public void tearDown() {
        //comisionDAO.deleteAll();
    }

    @Test
    public void testCrearComision() {
        Comision nuevaComision = new Comision("Comision 3");
        Comision comisionGuardada = comisionService.crear(nuevaComision);

        assertNotNull(comisionGuardada.getId());
        assertEquals("Comision 3", comisionGuardada.getNombre());
    }

    @Test
    public void testCrearTodasLasComisiones() {
        comisionDAO.deleteAll();
        List<Comision> comisiones = new ArrayList<>();
        comisiones.add(new Comision("Comision A"));
        comisiones.add(new Comision("Comision B"));

        comisionService.crearTodos(comisiones);

        assertEquals(2, comisionDAO.count());
    }

    @Test
    public void testObtenerComisionPorId() {
        Comision comisionEncontrada = comisionService.obtenerPorId(comision.getId());

        assertNotNull(comisionEncontrada);
        assertEquals(comision.getId(), comisionEncontrada.getId());
        assertEquals("Comision 1", comisionEncontrada.getNombre());
    }

    @Test
    public void testModificarComisionPorId() {
        Comision comisionModificada = new Comision("Comision Modificada");
        Comision comisionActualizada = comisionService.modificarPorId(comision.getId(), comisionModificada);

        assertEquals(comision.getId(), comisionActualizada.getId());
        assertEquals("Comision Modificada", comisionActualizada.getNombre());
    }

    @Test
    public void testEliminarComisionPorId() {
        comisionService.eliminarPorId(comision.getId());
        assertNull(comisionDAO.findById(comision.getId()).orElse(null));
    }

    @Test
    public void testEliminarTodasLasComisiones() {
        comisionService.eliminarTodo();
        assertEquals(0, comisionDAO.count());
    }
}
