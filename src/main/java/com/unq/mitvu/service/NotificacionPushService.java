package com.unq.mitvu.service;

public interface NotificacionPushService {
    void enviarNotificacion(String tokenDestino, String titulo, String cuerpo);
}
