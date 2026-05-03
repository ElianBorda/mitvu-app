package com.unq.mitvu.service;

import com.unq.mitvu.dao.EventoDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.mapper.EventoMapper;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Evento;
import com.unq.mitvu.model.Rol;
import com.unq.mitvu.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoServiceImpl implements EventoService{

    @Autowired
    private EventoMapper eventoMapper;

    private EventoDAO eventoDAO;
    private TutorDAO tutorDAO;

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
    public Evento crearParaCalendario(Evento evento, Usuario usuario) {
        evento.setCreadoPorId(usuario.getId());
        if (usuario.getRol() == Rol.ADMIN) {
            if (evento.getIdComision() == null || evento.getIdComision().isEmpty()) {
                evento.setEsGlobal(true);
            } else {
                evento.setEsGlobal(false);
            }
        }
        else {
            throw new SecurityException("No tienes permisos para crear eventos.");
        }
        return this.crear(evento);
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
        Evento eventoExistente = this.obtenerPorId(id);
        eventoMapper.actualizarEvento(evento, eventoExistente);
        return eventoDAO.save(eventoExistente);
    }

    @Override
    public void eliminarPorId(String id) {
        this.obtenerPorId(id);
        eventoDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        eventoDAO.deleteAll();
    }
}
