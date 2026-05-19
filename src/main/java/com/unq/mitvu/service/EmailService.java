package com.unq.mitvu.service;

import com.unq.mitvu.model.Estudiante;

public interface EmailService {
    void enviarCorreoDeAvisoDeFaltasAEstudiante(String nombreEstudiante, Integer cantidadDeFaltas);
}
