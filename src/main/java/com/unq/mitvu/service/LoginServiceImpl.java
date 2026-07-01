package com.unq.mitvu.service;

import com.unq.mitvu.dao.AdministradorDAO;
import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.CredencialesInvalidasException;
import com.unq.mitvu.model.Administrador;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Tutor;
import com.unq.mitvu.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AdministradorDAO administradorDAO;
    private final TutorDAO tutorDAO;
    private final EstudianteDAO estudianteDAO;

    @Override
    public Usuario autenticar(String dni, String password) {
        Usuario usuarioEncontrado = buscarUsuarioPorDni(dni);

        if (usuarioEncontrado == null || !usuarioEncontrado.getPassword().equals(password)) {
            throw new CredencialesInvalidasException("DNI o contraseña incorrectos.");
        }

        return usuarioEncontrado;
    }

    @Override
    public void registrarNuevaPassword(String dni, String passwordTemporal, String passwordNueva) {
        Usuario usuarioEncontrado = buscarUsuarioPorDni(dni);

        if (usuarioEncontrado == null || !usuarioEncontrado.getPassword().equals(passwordTemporal)) {
            throw new CredencialesInvalidasException("Credenciales inválidas para realizar el cambio.");
        }

        usuarioEncontrado.setPassword(passwordNueva);
        usuarioEncontrado.setRequiereCambioPassword(false);

        switch (usuarioEncontrado.getRol()) {
            case ADMIN -> administradorDAO.save((Administrador) usuarioEncontrado);
            case TUTOR -> tutorDAO.save((Tutor) usuarioEncontrado);
            case ESTUDIANTE -> estudianteDAO.save((Estudiante) usuarioEncontrado);
        }
    }

    private Usuario buscarUsuarioPorDni(String dni) {
        Optional<Administrador> admin = administradorDAO.findByDni(dni);
        if (admin.isPresent()) return admin.get();

        Optional<Tutor> tutor = tutorDAO.findByDni(dni);
        if (tutor.isPresent()) return tutor.get();

        Optional<Estudiante> estudiante = estudianteDAO.findByDni(dni);
        return estudiante.orElse(null);
    }
}