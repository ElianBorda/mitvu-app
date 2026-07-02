package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.FormularioBaja;
import com.unq.mitvu.model.MotivoBaja;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MetricaServiceTest {

    @Autowired
    private MetricaService metricaService;

    @Autowired
    private EstudianteDAO estudianteDAO;

    @Autowired
    private ComisionDAO comisionDAO;

    private Comision comisionActiva;
    private Comision comisionVacia;

    @BeforeEach
    void setUp() {
        limpiarBaseDeDatos();

        // 1. Setup de Comisiones
        comisionActiva = new Comision();
        comisionActiva.setNumero(1);
        comisionActiva = comisionDAO.save(comisionActiva);

        comisionVacia = new Comision();
        comisionVacia.setNumero(2);
        comisionVacia = comisionDAO.save(comisionVacia);

        // 2. Setup Estudiante 1: Activo
        Estudiante estudianteActivo = new Estudiante();
        estudianteActivo.setNombre("Alumno Activo");
        estudianteActivo.setComision(comisionActiva);
        estudianteDAO.save(estudianteActivo);

        // 3. Setup Estudiante 2: Dado de Baja (Falta de tiempo)
        FormularioBaja bajaFaltaTiempo = new FormularioBaja();
        bajaFaltaTiempo.setMotivo(MotivoBaja.FALTA_DE_TIEMPO);
        bajaFaltaTiempo.setIdComisionDadoDeBaja(comisionActiva.getId()); // Guardamos el historial
        bajaFaltaTiempo.setFechaBaja(LocalDate.now());

        Estudiante estudianteBaja = new Estudiante();
        estudianteBaja.setNombre("Alumno Baja 1");
        estudianteBaja.setBaja(bajaFaltaTiempo);
        estudianteBaja.setComision(null); // Regla de negocio respetada
        estudianteDAO.save(estudianteBaja);

        // 4. Setup Estudiante 3: Dado de Baja (Otro motivo)
        FormularioBaja bajaOtro = new FormularioBaja();
        bajaOtro.setMotivo(MotivoBaja.OTRO);
        bajaOtro.setIdComisionDadoDeBaja(comisionActiva.getId()); // Guardamos el historial
        bajaOtro.setFechaBaja(LocalDate.now());

        Estudiante estudianteBaja2 = new Estudiante();
        estudianteBaja2.setNombre("Alumno Baja 2");
        estudianteBaja2.setBaja(bajaOtro);
        estudianteBaja2.setComision(null); // Regla de negocio respetada
        estudianteDAO.save(estudianteBaja2);
    }

    @AfterEach
    void tearDown() {
        limpiarBaseDeDatos();
    }

    private void limpiarBaseDeDatos() {
        estudianteDAO.deleteAll();
        comisionDAO.deleteAll();
    }

    @Test
    void testCantidadTotalDeEstudiantes() {
        Integer total = metricaService.cantidadTotalDeEstudiantes();
        assertEquals(3, total);
    }

    @Test
    void testCantidadDeEstudiantesActivos() {
        Integer activos = metricaService.cantidadDeEstudiantesActivos();
        assertEquals(1, activos);
    }

    @Test
    void testCantidadDeEstudiantesDadosDeBaja() {
        Integer bajas = metricaService.cantidadDeEstudiantesDadosDeBaja();
        assertEquals(2, bajas);
    }

    @Test
    void testCantidadDeEstudiantesDadosDeBajaDeUnaComision_Existente() {
        Integer bajasComision = metricaService.cantidadDeEstudiantesDadosDeBajaDeUnaComision(comisionActiva.getId());
        assertEquals(2, bajasComision);
    }

    @Test
    void testCantidadTotalDeEstudiantesDeUnaComision_Existente() {
        Integer totalComisionActiva = metricaService.cantidadTotalDeEstudiantesDeUnaComision(comisionActiva.getId());
        assertEquals(3, totalComisionActiva);

        Integer totalComisionVacia = metricaService.cantidadTotalDeEstudiantesDeUnaComision(comisionVacia.getId());
        assertEquals(0, totalComisionVacia);
    }

    @Test
    void testCantidadTotalDeEstudiantesActivosDeUnaComision_Existente() {
        Integer activosComision = metricaService.cantidadTotalDeEstudiantesActivosDeUnaComision(comisionActiva.getId());
        assertEquals(1, activosComision);
    }

    @Test
    void testCantidadTotalDeEstudianesDadosDeBajaPorMotivoDeUnaComision_Existente() {
        Integer bajasPorOtro = metricaService.cantidadTotalDeEstudianesDadosDeBajaPorMotivoDeUnaComision(comisionActiva.getId(), MotivoBaja.OTRO);
        assertEquals(1, bajasPorOtro, "Debe encontrar al alumno que se dio de baja por motivo OTRO y que pertenecía a la comisión");

        Integer bajasPorFaltaTiempo = metricaService.cantidadTotalDeEstudianesDadosDeBajaPorMotivoDeUnaComision(comisionActiva.getId(), MotivoBaja.FALTA_DE_TIEMPO);
        assertEquals(1, bajasPorFaltaTiempo);
    }

    @Test
    void testCantidadDeEstudiantesDadosDeBajaPorMotivo() {
        Integer bajasPorFaltaDeTiempo = metricaService.cantidadDeEstudiantesDadosDeBajaPorMotivo(MotivoBaja.FALTA_DE_TIEMPO);
        assertEquals(1, bajasPorFaltaDeTiempo);

        Integer bajasPorBurocracia = metricaService.cantidadDeEstudiantesDadosDeBajaPorMotivo(MotivoBaja.DESACUERDO_BUROCRACIA);
        assertEquals(0, bajasPorBurocracia);
    }

    @Test
    void testMetricasConComisionInexistente_LanzaRecursoNoEncontradoException() {
        String idInvalido = "comision-inexistente-123";

        assertThrows(RecursoNoEncontradoException.class, () ->
                metricaService.cantidadDeEstudiantesDadosDeBajaDeUnaComision(idInvalido)
        );

        assertThrows(RecursoNoEncontradoException.class, () ->
                metricaService.cantidadTotalDeEstudiantesDeUnaComision(idInvalido)
        );

        assertThrows(RecursoNoEncontradoException.class, () ->
                metricaService.cantidadTotalDeEstudiantesActivosDeUnaComision(idInvalido)
        );

        assertThrows(RecursoNoEncontradoException.class, () ->
                metricaService.cantidadTotalDeEstudianesDadosDeBajaPorMotivoDeUnaComision(idInvalido, MotivoBaja.FALTA_DE_TIEMPO)
        );
    }
}