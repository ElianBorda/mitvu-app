package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.model.Comision;
import org.springframework.stereotype.Service;

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
        Comision comision1 = comisionDAO.getById(id);
        comision1.setNombre(comision.getNombre());

        return comisionDAO.save(comision1);
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
