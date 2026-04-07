package com.unq.mitvu.service;

import com.unq.mitvu.model.Tutor;

import java.util.List;

public interface TutorService {
    Tutor crear(Tutor tutor);
    void crearTodos(List<Tutor> tutores);
    Tutor obtenerPorId(String id);
    Tutor modificarPorId(String id, Tutor tutor);
    void eliminarPorId(String id);
    void eliminarTodo();
}
