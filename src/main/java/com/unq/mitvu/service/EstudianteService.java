package com.unq.mitvu.service;

import com.unq.mitvu.body.EstudianteBody;
import com.unq.mitvu.model.Estudiante;

import java.util.List;

public interface EstudianteService {
    Estudiante crear(Estudiante estudiante);
    void crearTodos(List<Estudiante> estudiantes);
    Estudiante obtenerPorId(String id);
    Estudiante modificarPorId(String id, Estudiante estudiante);
    void eliminarPorId(String id);
    void eliminarTodo();
}
