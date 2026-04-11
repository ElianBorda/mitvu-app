package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Turno;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ComisionServiceImpl implements ComisionService {

    private final ComisionDAO comisionDAO;

    public ComisionServiceImpl(ComisionDAO comisionDAO) {
        this.comisionDAO = comisionDAO;
    }

    @Override
    public Comision crear(Comision comision) {
        return comisionDAO.save(comision);
    }
    @Override
    public void crearTodos(List<Comision> comisiones) {
        comisionDAO.saveAll(comisiones);
    }

    @Override
    public Comision obtenerPorId(String id) {
        return comisionDAO.getById(id);
    }

    @Override
    public Comision modificarPorId(String id, Comision comision) {
        Comision comisionRecuperada = comisionDAO.getById(id);
        comisionRecuperada.setAula(comision.getAula());
        comisionRecuperada.setCarrera(comision.getCarrera());
        comisionRecuperada.setDepartamento(comision.getDepartamento());
        comisionRecuperada.setLocalidad(comision.getLocalidad());
        comisionRecuperada.setNumero(comision.getNumero());
        comisionRecuperada.setTurno(comision.getTurno());
        comisionRecuperada.setTutor(comision.getTutor());
        return comisionDAO.save(comisionRecuperada);
    }

    @Override
    public List<Comision> obtenerTodos() {
        return comisionDAO.findAll();
    }

    @Override
    public void eliminarPorId(String id) {
        comisionDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        comisionDAO.deleteAll();
    }
}
