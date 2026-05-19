package com.unq.mitvu.controller;

import com.unq.mitvu.config.RabbitMQConfig;
import com.unq.mitvu.controller.dto.NotificacionFaltaDTO;
import com.unq.mitvu.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EmailListenerController {
    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void recibirOrdenDeCorreo(NotificacionFaltaDTO dto) {
        emailService.enviarCorreoDeAvisoDeFaltasAEstudiante(
                dto.nombreEstudiante,
                dto.cantidadDeFaltas
        );
    }
}
