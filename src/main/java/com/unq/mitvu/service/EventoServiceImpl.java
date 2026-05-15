package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.EventoDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import com.unq.mitvu.mapper.EventoMapper;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Evento;
import com.unq.mitvu.model.Rol;
import com.unq.mitvu.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EventoServiceImpl implements EventoService{

    @Autowired
    private EventoMapper eventoMapper;

    private EventoDAO eventoDAO;
    private ComisionDAO comisionDAO;
    private TutorDAO tutorDAO;

    @Override
    public Evento crear(Evento evento) {
        if (evento.getIdComision() != null){
            comisionDAO.findById(evento.getIdComision()).orElseThrow(
                    () -> new ReglaDeNegocioException("No se puede asignar el evento a la COMISIÓN con id: " + evento.getIdComision() + " porque no existe"));
        }
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
                () -> new RecursoNoEncontradoException(id, "No se encontró el EVENTO con id: " + id)
        );
    }

    @Override
    public List<Evento> obtenerTodos() {
        return eventoDAO.findAll();
    }

    @Override
    public List<Evento> obtenerTodosLosEventosGlobales() {
        return eventoDAO.findByEsGlobalTrue();
    }

    @Override
    public List<Evento> obtenerTodosLosEventosParaComision(String idComision) {
        comisionDAO.findById(idComision).orElseThrow(
                () -> new RecursoNoEncontradoException(idComision, "No se encontró la COMISIÓN con id: " + idComision));
        return eventoDAO.findByEsGlobalTrueOrIdComision(idComision);
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
