package com.unq.mitvu.service;

import com.unq.mitvu.dao.SolicitudTutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.model.EstadoSolicitud;
import com.unq.mitvu.model.Rol;
import com.unq.mitvu.model.SolicitudTutor;
import com.unq.mitvu.model.Tutor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SolicitudTutorServiceImpl implements SolicitudTutorService {

    private final SolicitudTutorDAO solicitudTutorDAO;
    private final TutorService tutorService;

    @Override
    public SolicitudTutor crearSolicitud(SolicitudTutor solicitud) {
        solicitud.setFechaPostulacion(LocalDateTime.now());
        solicitud.setEstadoSolicitud(EstadoSolicitud.PENDIENTE);
        return solicitudTutorDAO.save(solicitud);
    }

    @Override
    public List<SolicitudTutor> obtenerPendientes() {
        return solicitudTutorDAO.findByEstadoSolicitud(EstadoSolicitud.PENDIENTE);
    }

    @Override
    public Tutor aprobarSolicitud(String idSolicitud) {
        SolicitudTutor solicitud = solicitudTutorDAO.findById(idSolicitud)
                .orElseThrow(() -> new RecursoNoEncontradoException(idSolicitud, "No se encontró la solicitud"));

        if (solicitud.getEstadoSolicitud() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Esta solicitud ya fue procesada");
        }

        Tutor nuevoTutor = new Tutor(
                solicitud.getApellido(),
                solicitud.getNombre(),
                solicitud.getDni(),
                solicitud.getCorreo(),
                "temporal123",
                Rol.TUTOR
        );

        Tutor tutorGuardado = tutorService.crear(nuevoTutor);
        solicitud.setEstadoSolicitud(EstadoSolicitud.APROBADA);
        solicitudTutorDAO.save(solicitud);

        return tutorGuardado;
    }

    @Override
    public SolicitudTutor rechazarSolicitud(String idSolicitud) {
        SolicitudTutor solicitud = solicitudTutorDAO.findById(idSolicitud)
                .orElseThrow(() -> new RecursoNoEncontradoException(idSolicitud, "No se encontró la solicitud"));

        solicitud.setEstadoSolicitud(EstadoSolicitud.RECHAZADA);
        return solicitudTutorDAO.save(solicitud);
    }
}