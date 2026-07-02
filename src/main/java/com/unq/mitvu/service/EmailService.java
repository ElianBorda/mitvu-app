package com.unq.mitvu.service;

import com.unq.mitvu.model.Estudiante;

public interface EmailService {
    void enviarCorreoDeAvisoDeFaltasAEstudiante(String nombreEstudiante, Integer cantidadDeFaltas);
    void enviarCorreoDeAnuncio(String nombreEstudiante, String titulo, String descripcion);
    void enviarCorreoSolicitudAprobada(String correoDestino, String nombreTutor, String passwordTemporal);
    void enviarCorreoSolicitudRechazada(String correoDestino, String nombreTutor);
}
