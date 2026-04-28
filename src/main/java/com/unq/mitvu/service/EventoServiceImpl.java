package com.unq.mitvu.service;

import com.unq.mitvu.dao.EventoDAO;
import com.unq.mitvu.model.Evento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService{

    private EventoDAO eventoDAO;

    public EventoServiceImpl(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    @Override
    public Evento crear(Evento evento) {
        return null;
    }

    @Override
    public List<Evento> crearTodos(List<Evento> eventos) {
        return List.of();
    }

    @Override
    public Evento obtenerPorId(String id) {
        return null;
    }

    @Override
    public List<Evento> obtenerTodos() {
        return List.of();
    }

    @Override
    public Evento modificarPorId(String id, Evento evento) {
        return null;
    }

    @Override
    public void eliminarPorId(String id) {

    }

    @Override
    public void eliminarTodo() {

    }
}
