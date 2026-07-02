package com.unq.mitvu.service;

import com.unq.mitvu.model.Administrador;

import java.util.List;

public interface AdministradorService {
    List<Administrador> obtenerTodos();

    Administrador obtenerPorId(String id);

    Administrador crear(Administrador nuevoAdministrador);

    List<Administrador> crearTodos(List<Administrador> administradores);

    void eliminarPorId(String id);

    void eliminarTodo();

    Administrador modificarPorId(String id, Administrador administrador);
}
