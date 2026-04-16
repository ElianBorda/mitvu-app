package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Turno;
import com.unq.mitvu.model.Tutor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComisionServiceImpl implements ComisionService {

    private final ComisionDAO comisionDAO;

    public ComisionServiceImpl(ComisionDAO comisionDAO) {
        this.comisionDAO = comisionDAO;
    }

    @Override
    public Comision crear(Comision comision) {
        if (comision.getNumero() == null) {
            Integer numeroDeComision = Math.toIntExact(comisionDAO.countComisionsByDepartamentoAndLocalidadAndCarrera(
                    comision.getDepartamento(),
                    comision.getLocalidad(),
                    comision.getCarrera()
            ));
            comision.setNumero(numeroDeComision + 1);
        }
        return comisionDAO.save(comision);
    }

    @Override
    public List<Comision> obtenerSinTutor() {
        return comisionDAO.findSinTutor();
    }

    @Override
    public void crearTodos(List<Comision> comisiones) {
        comisionDAO.saveAll(comisiones);
    }

    @Override
    public Comision obtenerPorId(String id) {
        return comisionDAO.getById(id);
    }

    @Override
    public ArrayList<Comision> obtenerTodosPorId(ArrayList<String> ids) {
        return (ArrayList<Comision>) comisionDAO.findAllById(ids);
    }

    @Override
    public Comision modificarPorId(String id, Comision comision) {
        Comision comisionRecuperada = comisionDAO.getById(id);
        // Si lo que llega de comision es null, que no haga nada

        comisionRecuperada.setAula(comision.getAula());
        comisionRecuperada.setCarrera(comision.getCarrera());
        comisionRecuperada.setDepartamento(comision.getDepartamento());
        comisionRecuperada.setLocalidad(comision.getLocalidad());
        comisionRecuperada.setHorarioInicio(comision.getHorarioInicio());
        comisionRecuperada.setHorarioFin(comision.getHorarioFin());
        comisionRecuperada.setTurno(comision.getTurno());

        return comisionDAO.save(comisionRecuperada);
    }

    @Override
    public List<Comision> obtenerTodos() {
        return comisionDAO.findAll();
    }

    @Override
    public void eliminarPorId(String id) {
        comisionDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        comisionDAO.deleteAll();
    }

    @Override
    public void agregarTutorAComisiones(Tutor tutor, ArrayList<String> comisiones_ids) {
        comisiones_ids.forEach(id -> {
            Comision comisionDB = comisionDAO.getById(id);
            comisionDB.setTutor(tutor);
            comisionDAO.save(comisionDB);
        });
    }

    @Override
    public void agregarEstudianteAComision(Estudiante estudianteGuardado, String comision_id) {
        Comision comisionSinEst = comisionDAO.getById(comision_id);

        List<Estudiante> estudiantesComision = new ArrayList<>(comisionSinEst.getEstudiantes());
        estudiantesComision.add(estudianteGuardado);
        ArrayList<Estudiante> estudiantesAux = new ArrayList<>(estudiantesComision);

        comisionSinEst.setEstudiantes(estudiantesAux);
        comisionDAO.save(comisionSinEst);
    }

}
