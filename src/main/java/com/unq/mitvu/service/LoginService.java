package com.unq.mitvu.service;

import com.unq.mitvu.model.Usuario;

public interface LoginService {
    Usuario autenticar(String dni, String password);
    void registrarNuevaPassword(String dni, String passwordTemporal, String passwordNueva);
}