package com.unq.mitvu.service;

import com.unq.mitvu.model.Estudiante;

import java.util.List;

public interface EstudianteService {
    Estudiante crear(Estudiante estudiante);
    void crearTodos(List<Estudiante> estudiantes);
    Estudiante obtenerPorId(String id);
    List<Estudiante> obtenerTodos();
    Estudiante modificarPorId(String id, Estudiante estudiante);
    void eliminarPorId(String id);
    void eliminarTodo();
    Estudiante agregarEstudianteAComision(String idEstudiante, String idComision);
    Estudiante eliminarComisionDeEstudiante(String idEstudiante);
    List<Estudiante> obtenerEstudiantesDeComision(String idComision);
    List<Estudiante> eliminarLaComisionDeTodosLosEstudiantes(String idComision);
    List<Estudiante> eliminarTodasLasComisionesDeTodosLosEstudiantes();
    Estudiante cambiarEstudianteDeComision(String idEstudiante, String idComision);
    List<Estudiante> cambiarEstudiantesAComision(List<String> idsEstudiantes, String idComision);
    List<Estudiante> obtenerTodosLosEstudiantesDeTutor(String idTutor);
    void darseDeBaja(String idEstudiante);
}
