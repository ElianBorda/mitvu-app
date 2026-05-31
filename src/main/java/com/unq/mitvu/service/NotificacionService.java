package com.unq.mitvu.service;

import com.unq.mitvu.model.Notificacion;

import java.util.List;

public interface NotificacionService {
    Notificacion crear(Notificacion notificacion);
    List<Notificacion> obtenerPorUsuario(String idUsuario);
    Notificacion marcarNotificacionComoLeida(String idNotificacion);
}
