package com.unq.mitvu.service;

import com.unq.mitvu.model.Comision;

import java.util.ArrayList;
import java.util.List;

public interface ComisionService {
    Comision crear(Comision comision);
    void crearTodos(List<Comision> comisiones);
    Comision modificarPorId(String id, Comision comision);
    void modificarTodos(List<String> ids, Comision comision);
    Comision obtenerPorId(String id);
    void eliminarPorId(String id);
    void eliminarTodo();
    Comision agregarTutorAComision(String idTutor, String idComision);
    Comision cambiarDeTutorEnComision(String idTutor, String idComision);
    List<Comision> agregarTutorAComisionesPorId(String idTutor, List<String> idsComisiones);
    List<Comision> obtenerComisionesDeTutor(String idTutor);
    List<Comision> obtenerComisionesSinTutor();
    List<Comision> obtenerTodos();
    Comision eliminarTutorDeComision(String idComision);
    List<Comision> eliminarTutorDeComisiones(String idTutor);
    List<Comision> eliminarTodosLosTutoresDeTodasLasComisiones();

}
