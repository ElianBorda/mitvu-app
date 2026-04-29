package com.unq.mitvu.service;

import com.unq.mitvu.model.Evento;

import java.util.List;

public interface EventoService {

    Evento crear(Evento evento);
    List<Evento> crearTodos(List<Evento> eventos);
    Evento crearParaCalendario(Evento evento);
    Evento obtenerPorId(String id);
    List<Evento> obtenerTodos();
    Evento modificarPorId(String id, Evento evento);
    void eliminarPorId(String id);
    void eliminarTodo();
}
