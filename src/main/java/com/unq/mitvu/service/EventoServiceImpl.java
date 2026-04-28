package com.unq.mitvu.service;

import com.unq.mitvu.dao.EventoDAO;
import com.unq.mitvu.mapper.EventoMapper;
import com.unq.mitvu.model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService{

    @Autowired
    private EventoMapper eventoMapper;

    private EventoDAO eventoDAO;

    public EventoServiceImpl(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    @Override
    public Evento crear(Evento evento) {
        return eventoDAO.save(evento);
    }

    @Override
    public List<Evento> crearTodos(List<Evento> eventos) {
        return List.of();
    }

    @Override
    public Evento obtenerPorId(String id) {
        return eventoDAO.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontró el EVENTO con id: " + id)
        );
    }

    @Override
    public List<Evento> obtenerTodos() {
        return eventoDAO.findAll();
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
