package com.unq.mitvu.service;

import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Turno;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ComisionServiceImpl implements ComisionService {

    private final ComisionDAO comisionDAO;

    public ComisionServiceImpl(ComisionDAO comisionDAO) {
        this.comisionDAO = comisionDAO;
    }

    @Override
    public Comision crear(Comision comision) {
        List<Comision> comisionesExistentes = comisionDAO.findByLocalidadAndDepartamentoAndCarrera(
                comision.getLocalidad(),
                comision.getDepartamento(),
                comision.getCarrera()
        );
        comision.setNumero(comisionesExistentes.size() + 1);

        comision.setTurno(determinarTurno(comision.getHorario()));

        return comisionDAO.save(comision);
    }

    private Turno determinarTurno(String horario) {
        String[] partes = horario.split(" a ");
        String horaInicioStr = partes[0].split(":")[0];
        int horaInicio = Integer.parseInt(horaInicioStr);

        if (horaInicio >= 8 && horaInicio < 12) {
            return Turno.MANANA;
        } else if (horaInicio >= 12 && horaInicio < 18) {
            return Turno.TARDE;
        } else {
            return Turno.NOCHE;
        }
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
    public Comision modificarPorId(String id, Comision comision) {
        Comision comision1 = comisionDAO.getById(id);
        // No se puede modificar el nombre, se elimina el campo
        // comision1.setNombre(comision.getNombre());

        return comisionDAO.save(comision1);
    }

    @Override
    public void eliminarPorId(String id) {
        comisionDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        comisionDAO.deleteAll();
    }
}
