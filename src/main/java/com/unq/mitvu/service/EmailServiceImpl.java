package com.unq.mitvu.service;

import com.unq.mitvu.model.Estudiante;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Override
    public void enviarCorreoDeAvisoDeFaltasAEstudiante(String nombreEstudiante, Integer cantidadDeFaltas) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("eliancamiloalejandro@gmail.com");
            helper.setTo("eliancamiloalejandro@gmail.com");
            helper.setSubject("Aviso: Límite de Faltas en tutoria");

            String htmlContent = """
                <div style="font-family: Arial;">
                    <h2>Aviso de Estado de Cursada</h2>
                    <p>Hola <strong>%s</strong>, tienes <strong>%d faltas</strong> en <em>tutorias</em>.</p>
                </div>
                """.formatted(nombreEstudiante, cantidadDeFaltas);

            helper.setText(htmlContent, true);
            mailSender.send(mensaje);

        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo SMTP", e);
        }
    }
}
