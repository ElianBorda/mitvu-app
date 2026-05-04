package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import com.unq.mitvu.mapper.TutorMapper;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Rol;
import com.unq.mitvu.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorDAO tutorDAO;
    private final ComisionDAO comisionDAO;

    @Autowired
    private TutorMapper tutorMapper;

    public TutorServiceImpl(TutorDAO tutorDAO, ComisionDAO comisionDAO) {
        this.tutorDAO = tutorDAO;
        this.comisionDAO = comisionDAO;
    }

    @Override
    public Tutor crear(Tutor tutor) {
        tutor.setRol(Rol.TUTOR);
        return tutorDAO.save(tutor);
    }

    @Override
    public void crearTodos(List<Tutor> tutores) {
        tutores.forEach(tutor -> tutor.setRol(Rol.TUTOR));
        tutorDAO.saveAll(tutores);
    }

    @Override
    public Tutor obtenerPorId(String id) {
        return tutorDAO.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException(id, "No se encontró el TUTOR con id: " + id));
    }

    @Override
    public List<Tutor> obtenerTodos() {
        return tutorDAO.findAll();
    }

    @Override
    public Tutor modificarPorId(String id, Tutor tutor) {
        Tutor tutorExistente = this.obtenerPorId(id);
        tutorMapper.actualizarTutor(tutor, tutorExistente);
        return tutorDAO.save(tutorExistente);
    }

    @Override
    public void eliminarPorId(String id) {
        this.obtenerPorId(id);

        List<Comision> comisiones = comisionDAO.findByTutorId(id);
        comisiones.forEach(comision -> comision.setTutor(null));
        comisionDAO.saveAll(comisiones);

        tutorDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        List<Comision> comisiones = comisionDAO.findAll();
        comisiones.forEach(comision -> comision.setTutor(null));
        comisionDAO.saveAll(comisiones);

        tutorDAO.deleteAll();
    }

    @Override
    public Tutor obtenerTutorDeLaComision(String idComision) {
        Comision comision = comisionDAO.getById(idComision);

        if (comision == null) {
            throw new RecursoNoEncontradoException(idComision, "No existe una comision con id: " + idComision);
        }

        Comision comisionConTutor = comisionDAO
                .findByIdAndTutorIsNotNull(idComision)
                .orElseThrow(() -> new ReglaDeNegocioException(
                        "La comision con id: " + idComision + " no tiene un tutor asignado"
                ));

        return comisionConTutor.getTutor();
    }
}