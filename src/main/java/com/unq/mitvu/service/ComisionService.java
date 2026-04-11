package com.unq.mitvu.service;

import com.unq.mitvu.model.Comision;

import java.util.List;

public interface ComisionService {
    Comision crear(Comision comision);
    void crearTodos(List<Comision> comisiones);
    Comision obtenerPorId(String id);
    Comision modificarPorId(String id, Comision comision);
    List<Comision> obtenerTodos();
    void eliminarPorId(String id);
    void eliminarTodo();

}
