package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import com.unq.mitvu.mapper.EstudianteMapper;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.FormularioBaja;
import com.unq.mitvu.model.Rol;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteDAO estudianteDAO;
    private final ComisionDAO comisionDAO;
    private final TutorDAO tutorDAO;

    @Autowired
    private EstudianteMapper estudianteMapper;

    public EstudianteServiceImpl(EstudianteDAO estudianteDAO, ComisionDAO comisionDAO, TutorDAO tutorDAO) {
        this.estudianteDAO = estudianteDAO;
        this.comisionDAO = comisionDAO;
        this.tutorDAO = tutorDAO;
    }

    @Override
    public Estudiante crear(Estudiante estudiante) {
        estudiante.setRol(Rol.ESTUDIANTE);
        return estudianteDAO.save(estudiante);
    }

    @Override
    public void crearTodos(List<Estudiante> estudiantes) {
        estudiantes.forEach(estudiante -> estudiante.setRol(Rol.ESTUDIANTE));
        estudianteDAO.saveAll(estudiantes);
    }

    @Override
    public Estudiante  obtenerPorId(String id) {
        return estudianteDAO.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException(id, "No se encontró el ESTUDIANTE con id: " + id));
    }

    @Override
    public List<Estudiante> obtenerTodos() {
        return estudianteDAO.findAll();
    }

    @Override
    public Estudiante modificarPorId(String id, Estudiante estudiante) {
        Estudiante estudianteExistente = this.obtenerPorId(id);
        estudianteMapper.actualizarEstudiante(estudiante, estudianteExistente);
        return estudianteDAO.save(estudianteExistente);
    }

    @Override
    public void eliminarPorId(String id) {
        this.obtenerPorId(id);
        estudianteDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        estudianteDAO.deleteAll();
    }

    @Override
    public Estudiante agregarEstudianteAComision(String idEstudiante, String idComision) {
        Estudiante estudiante = this.obtenerPorId(idEstudiante);
        if (!estudiante.estaActivo()){
            throw new ReglaDeNegocioException("No se puede asignar una comisión al ESTUDIANTE con id: " + idEstudiante + "  porque se encuentra dado de baja.");
        }
        Comision comision = comisionDAO.findById(idComision).orElseThrow(() ->
                new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision));

        if (estudiante.getComision() == null || estudiante.getComision().getId() == null){
            estudiante.setComision(comision);
            return estudianteDAO.save(estudiante);
        } else {
            throw new ReglaDeNegocioException("El ESTUDIANTE con id: " + idEstudiante + " ya se encuentra en la COMISION con id: " + estudiante.getComision().getId() + ".");
        }
    }

    @Override
    public Estudiante eliminarComisionDeEstudiante(String idEstudiante) {
        Estudiante estudiante = this.obtenerPorId(idEstudiante);
        if (estudiante.getComision() != null){
            estudiante.setComision(null);
            return estudianteDAO.save(estudiante);
        } else {
            throw new ReglaDeNegocioException("El ESTUDIANTE con id: " + idEstudiante + " no se encuentra en ninguna COMISION.");
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesDeComision(String idComision) {
        if (!comisionDAO.existsById(idComision)) {
            throw new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision);
        }
        return estudianteDAO.findByComisionId(new ObjectId(idComision));
    }

    @Override
    public List<Estudiante> eliminarLaComisionDeTodosLosEstudiantes(String idComision) {
        List<Estudiante> estudiantes = this.obtenerEstudiantesDeComision(idComision);
        estudiantes.forEach(estudiante -> this.eliminarComisionDeEstudiante(estudiante.getId()));
        return estudiantes;
    }

    @Override
    public List<Estudiante> eliminarTodasLasComisionesDeTodosLosEstudiantes() {
        return this.obtenerTodos().stream().map(estudiante -> this.eliminarComisionDeEstudiante(estudiante.getId())).toList();
    }

    @Override
    public Estudiante cambiarEstudianteDeComision(String idEstudiante, String idComision) {
        Estudiante estudiante = this.obtenerPorId(idEstudiante);
        Comision comision = comisionDAO.findById(idComision).orElseThrow(() ->
                new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision));

        if (estudiante.getComision() != null){
            estudiante.setComision(comision);
            return estudianteDAO.save(estudiante);
        } else {
            throw new ReglaDeNegocioException("El ESTUDIANTE con id: " + idEstudiante + " no se encuentra en ninguna COMISION.");
        }
    }

    @Override
    public List<Estudiante> cambiarEstudiantesAComision(List<String> idsEstudiantes, String idComision) {
        return idsEstudiantes.stream().map(id -> this.cambiarEstudianteDeComision(id, idComision)).toList();
    }

    @Override
    public List<Estudiante> obtenerTodosLosEstudiantesDeTutor(String idTutor) {
        if (!tutorDAO.existsById(idTutor)) {
            throw new RecursoNoEncontradoException(idTutor, "No se encontró el TUTOR con id: " + idTutor);
        }
        List<Comision> comisionesDelTutor = comisionDAO.findByTutorId(idTutor);
        if (comisionesDelTutor.isEmpty()) {
            return new ArrayList<>();
        }
        return estudianteDAO.findByComisionIn(comisionesDelTutor);
    }

    @Override
    public Estudiante darseDeBaja(String idEstudiante, FormularioBaja formularioBaja) {
        Estudiante estudiante = this.obtenerPorId(idEstudiante);
        if (!estudiante.estaActivo()) {
            throw new ReglaDeNegocioException("El estudiante con id " + idEstudiante + " ya se encuentra dado de baja." );
        }
        if (estudiante.getComision() == null || estudiante.getComision().getId() == null){
            throw new ReglaDeNegocioException("El estudiante con id " + idEstudiante + " no se encuentra en ninguna comisión.");
        }

        formularioBaja.setIdComisionDadoDeBaja(estudiante.getComision().getId());
        estudiante.setBaja(formularioBaja);
        estudiante.setComision(null);
        return estudianteDAO.save(estudiante);
    }

    @Override
    public List<Estudiante> obtenerTodosLosEstudiantesDadosDeBajaDeUnaComision(String idComision){
        if (!comisionDAO.existsById(idComision)) {
            throw new RecursoNoEncontradoException(idComision, "No se encontró la COMISION con id: " + idComision);
        }
        System.out.println("Entro a buscar por dao la comision con ID: " + idComision);
        return estudianteDAO.findByBaja_idComisionDadoDeBaja(idComision);
    }

    @Override
    public List<Estudiante> obtenerEstudiantesDeBaja() {
        List<Estudiante> estudiantesBaja = estudianteDAO.findByBajaIsNotNull();
        return estudiantesBaja;
    }

    @Override
    public List<Estudiante> obtenerEstudiantesActivos() {
        List<Estudiante> estudiantesBaja = estudianteDAO.findByBajaIsNull();
        return estudiantesBaja;
    }

    @Override
    public boolean estaDadoDeBaja(String idEstudiante) {
        Estudiante estudiante = this.obtenerPorId(idEstudiante);
        return !estudiante.estaActivo();
    }
}