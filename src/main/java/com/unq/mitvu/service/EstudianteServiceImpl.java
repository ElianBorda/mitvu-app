package com.unq.mitvu.service;

import com.unq.mitvu.body.EstudianteBody;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.model.Estudiante;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private EstudianteDAO estudianteDAO;

    public EstudianteServiceImpl(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    public Estudiante crear(Estudiante estudiante) {
        return  estudianteDAO.save(estudiante);
    }

    @Override
    public void crearTodos(List<Estudiante> estudiantes) {
        estudianteDAO.saveAll(estudiantes);
    }

    @Override
    public Estudiante obtenerPorId(String id) {
        return estudianteDAO.getById(id);
    }

    @Override
    public List<Estudiante> obtenerTodos() { return estudianteDAO.findAll(); }

    @Override
    public Estudiante modificarPorId(String id, Estudiante estudiante) {
        Estudiante estudianteOg = estudianteDAO.getById(id);

        estudianteOg.setNombre(estudiante.getNombre());
        estudianteOg.setApellido(estudiante.getApellido());
        estudianteOg.setDni(estudiante.getDni());
        estudiante.setCarrera(estudiante.getCarrera());
        estudianteOg.setCantidad_asistencias(estudiante.getCantidad_asistencias());
        estudianteOg.setComision_id(estudiante.getComision_id());

        return estudianteDAO.save(estudianteOg);
    }

    @Override
    public void eliminarPorId(String id) {
        estudianteDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        estudianteDAO.deleteAll();
    }
}
