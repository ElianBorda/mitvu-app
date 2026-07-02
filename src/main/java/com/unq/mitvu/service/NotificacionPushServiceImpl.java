package com.unq.mitvu.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificacionPushServiceImpl implements NotificacionPushService{

    public void enviarNotificacion(String tokenDestino, String titulo, String cuerpo) {
        if (tokenDestino == null || tokenDestino.isEmpty()) {
            return;
        }

        try {
            Notification notification = Notification.builder()
                    .setTitle(titulo)
                    .setBody(cuerpo)
                    .setImage("/mitvubanner.png")
                    .build();

            Message message = Message.builder()
                    .setToken(tokenDestino)
                    .setNotification(notification)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificación push enviada con éxito: " + response);

        } catch (Exception e) {
            System.err.println("Error enviando notificación push: " + e.getMessage());
        }
    }
}