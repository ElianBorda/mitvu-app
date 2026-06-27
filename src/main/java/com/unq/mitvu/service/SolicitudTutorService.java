package com.unq.mitvu.service;

import com.unq.mitvu.model.SolicitudTutor;
import com.unq.mitvu.model.Tutor;
import java.util.List;

public interface SolicitudTutorService {
    SolicitudTutor crearSolicitud(SolicitudTutor solicitud);
    List<SolicitudTutor> obtenerPendientes();
    Tutor aprobarSolicitud(String idSolicitud);
    void rechazarSolicitud(String idSolicitud);
}