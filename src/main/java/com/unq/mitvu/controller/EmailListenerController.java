package com.unq.mitvu.controller;

import com.unq.mitvu.config.RabbitMQConfig;
import com.unq.mitvu.controller.dto.NotificacionAnuncioDTO;
import com.unq.mitvu.controller.dto.NotificacionFaltaDTO;
import com.unq.mitvu.controller.dto.NotificacionSolicitudDTO;
import com.unq.mitvu.model.Notificacion;
import com.unq.mitvu.service.EmailService;
import com.unq.mitvu.service.NotificacionPushService;
import com.unq.mitvu.service.NotificacionService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class EmailListenerController {
    private final EmailService emailService;
    private final NotificacionPushService pushNotificationService;
    private final NotificacionService notificacionService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void notificarCantidadDeAusencias(NotificacionFaltaDTO dto) {
        emailService.enviarCorreoDeAvisoDeFaltasAEstudiante(
                dto.nombreEstudiante,
                dto.cantidadDeFaltas
        );

        String titulo = "⚠️ Alerta de Faltas";
        String cuerpo = "Hola " + dto.nombreEstudiante + ", acumulaste " + dto.cantidadDeFaltas + " faltas. Por favor revisá tu situación.";

        pushNotificationService.enviarNotificacion(
                dto.getFcmToken(),
                titulo,
                cuerpo
        );

        Notificacion notificacion = Notificacion.builder()
                .idUsuario(dto.idEstudiante)
                .leida(false)
                .titulo(titulo)
                .cuerpo(cuerpo)
                .fecha(LocalDateTime.now())
                .build();
        notificacionService.crear(notificacion);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ANUNCIOS)
    public void notificarAnuncios(NotificacionAnuncioDTO dto) {
        emailService.enviarCorreoDeAnuncio(
                dto.nombreEstudiante,
                dto.tituloAnuncio,
                dto.descripcionAnuncio
        );

        String tituloPush = "📢 " + dto.tituloAnuncio;
        String cuerpoPush = dto.descripcionAnuncio;

        pushNotificationService.enviarNotificacion(
                dto.getFcmToken(),
                tituloPush,
                cuerpoPush
        );

        Notificacion notificacion = Notificacion.builder()
                .idUsuario(dto.idUsuario)
                .leida(false)
                .titulo(tituloPush)
                .cuerpo(cuerpoPush)
                .fecha(LocalDateTime.now())
                .build();

        notificacionService.crear(notificacion);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_SOLICITUDES)
    public void notificarResultadoSolicitud(NotificacionSolicitudDTO dto) {
        if (dto.isAprobado()) {
            emailService.enviarCorreoSolicitudAprobada(
                    dto.getCorreoDestino(),
                    dto.getNombreTutor(),
                    dto.getPasswordTemporal()
            );
        } else {
            emailService.enviarCorreoSolicitudRechazada(
                    dto.getCorreoDestino(),
                    dto.getNombreTutor()
            );
        }
    }
}
