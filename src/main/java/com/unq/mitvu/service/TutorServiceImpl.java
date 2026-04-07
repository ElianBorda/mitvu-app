package com.unq.mitvu.service;

import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.model.Tutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorDAO tutorDAO;

    public TutorServiceImpl(TutorDAO tutorDAO) {
        this.tutorDAO = tutorDAO;
    }

    @Override
    public Tutor crear(Tutor tutor) {
        return tutorDAO.save(tutor);
    }

    @Override
    public void crearTodos(List<Tutor> tutores) {
        tutorDAO.saveAll(tutores);
    }

    @Override
    public Tutor obtenerPorId(String id) {
        return tutorDAO.findById(id).orElse(null);
    }

    @Override
    public Tutor modificarPorId(String id, Tutor tutor) {
        Tutor tutorOg = tutorDAO.getById(id);
        if (tutorOg != null) {}

        tutorOg.setNombre(tutor.getNombre());
        tutorOg.setApellido(tutor.getApellido());
        tutorOg.setDni(tutor.getDni());
        tutorOg.setComisiones_ids(tutor.getComisiones_ids());

        return tutorDAO.save(tutorOg);
    }

    @Override
    public void eliminarPorId(String id) {
        tutorDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        tutorDAO.deleteAll();
    }
}
