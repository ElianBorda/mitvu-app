package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import com.unq.mitvu.mapper.ComisionMapper;
import com.unq.mitvu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComisionServiceImpl implements ComisionService {

    private final ComisionDAO comisionDAO;
    private final TutorDAO tutorDAO;
    private final EstudianteDAO estudianteDAO;

    @Autowired
    private ComisionMapper comisionMapper;

    public ComisionServiceImpl(ComisionDAO comisionDAO, TutorDAO tutorDAO, EstudianteDAO estudianteDAO) {
        this.comisionDAO = comisionDAO;
        this.tutorDAO = tutorDAO;
        this.estudianteDAO = estudianteDAO;
    }

    @Override
    public Comision crear(Comision comision) {
        comision.setNumero(Math.toIntExact(comisionDAO.countComisionsByDepartamentoAndLocalidadAndCarrera(comision.getDepartamento(), comision.getLocalidad(), comision.getCarrera())));
        return comisionDAO.save(comision);
    }

    @Override
    public void crearTodos(List<Comision> comisiones) {
        comisiones.forEach(comision -> {
            comision.setNumero(Math.toIntExact(comisionDAO.countComisionsByDepartamentoAndLocalidadAndCarrera(comision.getDepartamento(), comision.getLocalidad(), comision.getCarrera())));
        });
        comisionDAO.saveAll(comisiones);
    }

    @Override
    public Comision modificarPorId(String id, Comision comision) {
        Comision comisionExistente = this.obtenerPorId(id);
        comisionMapper.actualizarComision(comision, comisionExistente);
        return comisionDAO.save(comisionExistente);
    }

    @Override
    public void modificarTodos(List<String> ids, Comision comision) {
        ids.forEach(id -> this.modificarPorId(id, comision));
    }

    @Override
    public Comision obtenerPorId(String id) {
        return comisionDAO.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException(id, "No se encontró la COMISIÓN con id: " + id));
    }

    @Override
    public void eliminarPorId(String id) {
        List<Estudiante> estudiantes = estudianteDAO.findByComisionId(id);
        estudiantes.forEach(est -> est.setComision(null));
        estudianteDAO.saveAll(estudiantes);
        this.obtenerPorId(id);
        comisionDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        List<Estudiante> estudiantes = estudianteDAO.findAll();
        estudiantes.forEach(est -> est.setComision(null));
        estudianteDAO.saveAll(estudiantes);

        comisionDAO.deleteAll();
    }

    @Override
    public Comision agregarTutorAComision(String idTutor, String idComision) {
        Tutor tutor = tutorDAO.findById(idTutor).orElseThrow(() ->
                new RecursoNoEncontradoException(idTutor, "No se encontró el TUTOR con id: " + idTutor));

        Comision comision = this.obtenerPorId(idComision);

        if (comision.getTutor() == null || comision.getTutor().getId() == null){
            comision.setTutor(tutor);
            return comisionDAO.save(comision);
        } else {
            throw new ReglaDeNegocioException("La COMISION con id: " + idComision + " ya tiene un TUTOR con id: " + comision.getTutor().getId() + ".");
        }
    }

    @Override
    public Comision cambiarDeTutorEnComision(String idTutor, String idComision) {
        Tutor tutor = tutorDAO.findById(idTutor).orElseThrow(() ->
                new RecursoNoEncontradoException(idTutor, "No se encontró el TUTOR con id: " + idTutor));
        Comision comision = this.obtenerPorId(idComision);
        if (comision.getTutor() != null){
            comision.setTutor(tutor);
            return comisionDAO.save(comision);
        } else {
            throw new ReglaDeNegocioException("La COMISION con id: " + idComision + " no tiene un TUTOR.");
        }
    }

    @Override
    public List<Comision> agregarTutorAComisionesPorId(String idTutor, List<String> idsComisiones) {
        return idsComisiones.stream().map(id -> this.agregarTutorAComision(idTutor, id)).toList();
    }

    @Override
    public List<Comision> obtenerComisionesDeTutor(String idTutor) {
        if (!tutorDAO.existsById(idTutor)) {
            throw new RecursoNoEncontradoException(idTutor, "No se encontró el TUTOR con id: " + idTutor);
        }
        return comisionDAO.findByTutorId(idTutor);
    }

    @Override
    public List<Comision> obtenerComisionesSinTutor() {
        return comisionDAO.findByTutorIsEmpty();
    }

    @Override
    public List<Comision> obtenerTodos() {
        return comisionDAO.findAll();
    }

    @Override
    public Comision eliminarTutorDeComision(String idComision) {
        Comision comision = this.obtenerPorId(idComision);
        if (comision.getTutor() != null){
            comision.setTutor(null);
            return comisionDAO.save(comision);
        } else {
            throw new ReglaDeNegocioException("La COMISION con id: " + idComision + " no tiene un TUTOR.");
        }
    }

    @Override
    public List<Comision> eliminarTutorDeComisiones(String idTutor) {
        return this.obtenerComisionesDeTutor(idTutor).stream().map(comision -> this.eliminarTutorDeComision(comision.getId())).toList();
    }

    @Override
    public List<Comision> eliminarTodosLosTutoresDeTodasLasComisiones() {
        return this.obtenerTodos().stream().map(comision -> this.eliminarTutorDeComision(comision.getId())).toList();
    }
}