package com.unq.mitvu.service;

import com.unq.mitvu.dao.AdministradorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.mapper.AdministradorMapper;
import com.unq.mitvu.model.Administrador;
import com.unq.mitvu.model.Rol;
import com.unq.mitvu.model.Tutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorDAO administradorDAO;

    @Autowired
    AdministradorMapper  administradorMapper;

    public AdministradorServiceImpl(AdministradorDAO administradorDAO){
        this.administradorDAO = administradorDAO;
    }

    @Override
    public List<Administrador> obtenerTodos() {
        return administradorDAO.findAll();
    }

    @Override
    public Administrador obtenerPorId(String id) {
        return administradorDAO.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException(id, "No se encontró el ADMINISTRADOR con id: " + id));
    }

    @Override
    public Administrador crear(Administrador nuevoAdministrador) {
        nuevoAdministrador.setRol(Rol.ADMIN);
        return administradorDAO.save(nuevoAdministrador);
    }

    @Override
    public List<Administrador> crearTodos(List<Administrador> administradores) {
        administradores.forEach(admin -> admin.setRol(Rol.ADMIN));
        return administradorDAO.saveAll(administradores);
    }

    @Override
    public void eliminarPorId(String id) {
        this.obtenerPorId(id);
        administradorDAO.deleteById(id);
    }

    @Override
    public void eliminarTodo() {
        administradorDAO.deleteAll();
    }

    @Override
    public Administrador modificarPorId(String id, Administrador administrador) {
        Administrador administradorExistente = this.obtenerPorId(id);
        administradorMapper.actualizarAdministrador(administrador, administradorExistente);
        return administradorDAO.save(administradorExistente);
    }
}
