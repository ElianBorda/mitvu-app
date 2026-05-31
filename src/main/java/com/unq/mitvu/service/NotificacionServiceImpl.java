package com.unq.mitvu.service;

import com.unq.mitvu.dao.EstudianteDAO;
import com.unq.mitvu.dao.NotificacionDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.RecursoNoEncontradoException;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import com.unq.mitvu.model.Notificacion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificacionServiceImpl implements NotificacionService{

    private NotificacionDAO notificacionDAO;
    private EstudianteDAO estudianteDAO;
    private TutorDAO tutorDAO;


    @Override
    public Notificacion crear(Notificacion notificacion) {
        String idUsuario = notificacion.getIdUsuario();

        boolean existeComoEstudiante = estudianteDAO.existsById(idUsuario);
        boolean existeComoTutor = tutorDAO.existsById(idUsuario);
        boolean esAdminFijo = idUsuario.equals("Administrador");

        if (!existeComoEstudiante && !existeComoTutor && !esAdminFijo) {
            throw new ReglaDeNegocioException(
                    "No se puede crear la notificación. El usuario con ID " + idUsuario + " no existe en el sistema."
            );
        }

        if (notificacion.getFecha() == null) {
            notificacion.setFecha(LocalDateTime.now());
        }
        return notificacionDAO.save(notificacion);
    }

    @Override
    public List<Notificacion> obtenerPorUsuario(String idUsuario) {
        return notificacionDAO.findByIdUsuario(idUsuario);
    }

    @Override
    public Notificacion marcarNotificacionComoLeida(String idNotificacion) {
        Notificacion notificacion = notificacionDAO.findById(idNotificacion).orElseThrow(() ->
                new RecursoNoEncontradoException(idNotificacion, "No se encontró la NOTIFICACIÓN con id: " + idNotificacion)
        );
        notificacion.setLeida(true);
        return notificacionDAO.save(notificacion);
    }
}
