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
            helper.setSubject("⚠️ Aviso de faltas — Taller de Vida Universitaria");

            String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body style="margin: 0; padding: 0; background-color: #f5f5f5; font-family: 'Segoe UI', Arial, sans-serif;">

                <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f5f5f5; padding: 40px 0;">
                    <tr>
                        <td align="center">
                            <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.08);">

                                <!-- HEADER -->
                                <tr>
                                    <td style="background-color: #7B0D1E; padding: 32px 40px; text-align: center;">
                                        <p style="margin: 0; color: #ffffff; font-size: 13px; letter-spacing: 2px; text-transform: uppercase; font-weight: 300;">
                                            Universidad Nacional de Quilmes
                                        </p>
                                        <h1 style="margin: 8px 0 0 0; color: #ffffff; font-size: 28px; font-weight: 700; letter-spacing: 1px;">
                                            mi<span style="font-style: italic; font-weight: 300;">TVU</span>
                                        </h1>
                                        <p style="margin: 6px 0 0 0; color: rgba(255,255,255,0.75); font-size: 12px; font-weight: 300;">
                                            Taller de Vida Universitaria
                                        </p>
                                    </td>
                                </tr>

                                <!-- ALERTA -->
                                <tr>
                                    <td style="background-color: #fdf2f4; border-bottom: 3px solid #7B0D1E; padding: 16px 40px; text-align: center;">
                                        <p style="margin: 0; color: #7B0D1E; font-size: 14px; font-weight: 600; letter-spacing: 0.5px;">
                                            ⚠️ &nbsp; AVISO DE ESTADO DE CURSADA
                                        </p>
                                    </td>
                                </tr>

                                <!-- CUERPO -->
                                <tr>
                                    <td style="padding: 40px 40px 32px 40px;">
                                        <p style="margin: 0 0 16px 0; color: #1a1a1a; font-size: 16px;">
                                            Hola, <strong style="color: #7B0D1E;">%s</strong>
                                        </p>
                                        <p style="margin: 0 0 24px 0; color: #444444; font-size: 15px; line-height: 1.6;">
                                            Te informamos que registrás actualmente
                                            <strong style="color: #1a1a1a;">%d %s</strong>
                                            en el <strong>Taller de Vida Universitaria</strong>.
                                        </p>

                                        <!-- CARD DE FALTAS -->
                                        <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #fdf2f4; border-radius: 8px; border-left: 4px solid #7B0D1E; margin-bottom: 24px;">
                                            <tr>
                                                <td style="padding: 20px 24px;">
                                                    <p style="margin: 0 0 4px 0; color: #7B0D1E; font-size: 12px; font-weight: 600; text-transform: uppercase; letter-spacing: 1px;">
                                                        Faltas registradas
                                                    </p>
                                                    <p style="margin: 0; color: #7B0D1E; font-size: 36px; font-weight: 700;">
                                                        %d
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>

                                        <p style="margin: 0 0 8px 0; color: #444444; font-size: 14px; line-height: 1.6;">
                                            Recordá que superar el límite de faltas permitido puede comprometer tu continuidad en el taller.
                                            Si considerás que existe un error o querés regularizar tu situación, comunicate con tu tutor/a a la brevedad.
                                        </p>
                                    </td>
                                </tr>

                                <!-- DIVIDER -->
                                <tr>
                                    <td style="padding: 0 40px;">
                                        <hr style="border: none; border-top: 1px solid #eeeeee; margin: 0;">
                                    </td>
                                </tr>

                                <!-- FOOTER -->
                                <tr>
                                    <td style="padding: 24px 40px; text-align: center;">
                                        <p style="margin: 0 0 4px 0; color: #999999; font-size: 12px;">
                                            Este es un mensaje automático generado por <strong>miTVU</strong>.
                                        </p>
                                        <p style="margin: 0; color: #bbbbbb; font-size: 11px;">
                                            Universidad Nacional de Quilmes · Taller de Vida Universitaria
                                        </p>
                                    </td>
                                </tr>

                            </table>
                        </td>
                    </tr>
                </table>

            </body>
            </html>
            """.formatted(
                    nombreEstudiante,
                    cantidadDeFaltas, cantidadDeFaltas == 1 ? "falta" : "faltas",
                    cantidadDeFaltas
            );

            helper.setText(htmlContent, true);
            mailSender.send(mensaje);

        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo SMTP", e);
        }
    }

    @Override
    public void enviarCorreoDeAnuncio(String nombreEstudiante, String titulo, String descripcion) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("eliancamiloalejandro@gmail.com");
            // Apuntamos todos los correos a la dirección solicitada
            helper.setTo("eliancamiloalejandro@gmail.com");
            helper.setSubject("📢 Nuevo Anuncio: " + titulo + " — Taller de Vida Universitaria");

            String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body style="margin: 0; padding: 0; background-color: #f5f5f5; font-family: 'Segoe UI', Arial, sans-serif;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f5f5f5; padding: 40px 0;">
                    <tr>
                        <td align="center">
                            <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.08);">
                                <tr>
                                    <td style="background-color: #7B0D1E; padding: 32px 40px; text-align: center;">
                                        <p style="margin: 0; color: #ffffff; font-size: 13px; letter-spacing: 2px; text-transform: uppercase; font-weight: 300;">
                                            Universidad Nacional de Quilmes
                                        </p>
                                        <h1 style="margin: 8px 0 0 0; color: #ffffff; font-size: 28px; font-weight: 700; letter-spacing: 1px;">
                                            mi<span style="font-style: italic; font-weight: 300;">TVU</span>
                                        </h1>
                                        <p style="margin: 6px 0 0 0; color: rgba(255,255,255,0.75); font-size: 12px; font-weight: 300;">
                                            Taller de Vida Universitaria
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="background-color: #eef2f5; border-bottom: 3px solid #7B0D1E; padding: 16px 40px; text-align: center;">
                                        <p style="margin: 0; color: #2c3e50; font-size: 14px; font-weight: 600; letter-spacing: 0.5px;">
                                            📢 &nbsp; %s
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 40px 40px 32px 40px;">
                                        <p style="margin: 0 0 16px 0; color: #1a1a1a; font-size: 16px;">
                                            ¡Hola, <strong style="color: #7B0D1E;">%s</strong>! Un nuevo anuncio fue publicado en la página. <i>Accede para conocer el detalle</i>
                                        </p>
                                        <div style="background-color: #f8f9fa; border-left: 4px solid #7B0D1E; padding: 20px; border-radius: 4px; margin-bottom: 24px; color: #444444; font-size: 15px; line-height: 1.6; white-space: pre-wrap;">%s</div>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 0 40px;">
                                        <hr style="border: none; border-top: 1px solid #eeeeee; margin: 0;">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="padding: 24px 40px; text-align: center;">
                                        <p style="margin: 0 0 4px 0; color: #999999; font-size: 12px;">
                                            Este es un mensaje automático generado por <strong>miTVU</strong>.
                                        </p>
                                        <p style="margin: 0; color: #bbbbbb; font-size: 11px;">
                                            Universidad Nacional de Quilmes · Taller de Vida Universitaria
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """.formatted(titulo, nombreEstudiante, descripcion);

            helper.setText(htmlContent, true);
            mailSender.send(mensaje);

        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo SMTP", e);
        }
    }

    @Override
    public void enviarCorreoSolicitudAprobada(String correoDestino, String nombreTutor, String passwordTemporal) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("eliancamiloalejandro@gmail.com");
            helper.setTo("eliancamiloalejandro@gmail.com");
            helper.setSubject("Solicitud Aprobada — Taller de Vida Universitaria");

            String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <body style="margin: 0; padding: 0; background-color: #f5f5f5; font-family: 'Segoe UI', Arial, sans-serif;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="padding: 40px 0;">
                    <tr><td align="center">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.08);">
                            <tr>
                                <td style="background-color: #7B0D1E; padding: 32px 40px; text-align: center;">
                                    <h1 style="margin: 8px 0 0 0; color: #ffffff; font-size: 28px; font-weight: 700; letter-spacing: 1px;">mi<span style="font-style: italic; font-weight: 300;">TVU</span></h1>
                                </td>
                            </tr>
                            <tr>
                                <td style="background-color: #eef5ee; border-bottom: 3px solid #2e7d32; padding: 16px 40px; text-align: center;">
                                    <p style="margin: 0; color: #2e7d32; font-size: 14px; font-weight: 600;">✅ &nbsp; POSTULACIÓN APROBADA</p>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding: 40px 40px 32px 40px;">
                                    <p style="margin: 0 0 16px 0; color: #1a1a1a; font-size: 16px;">¡Bienvenido/a, <strong style="color: #7B0D1E;">%s</strong>!</p>
                                    <p style="color: #444444; font-size: 15px; line-height: 1.6;">Tu solicitud para ser tutor ha sido aprobada por la administración. Ya podés ingresar a la plataforma utilizando tu DNI y la siguiente contraseña temporal:</p>
                                    <div style="background-color: #f8f9fa; border-left: 4px solid #7B0D1E; padding: 20px; border-radius: 4px; margin: 24px 0; text-align: center;">
                                        <span style="font-size: 22px; font-weight: 700; color: #1a1a1a; letter-spacing: 2px;">%s</span>
                                    </div>
                                    <p style="color: #e53935; font-size: 13px; font-weight: 600;">Por cuestiones de seguridad, el sistema te solicitará cambiar esta contraseña durante tu primer inicio de sesión.</p>
                                </td>
                            </tr>
                        </table>
                    </td></tr>
                </table>
            </body>
            </html>
            """.formatted(nombreTutor, passwordTemporal);

            helper.setText(htmlContent, true);
            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo SMTP", e);
        }
    }

    @Override
    public void enviarCorreoSolicitudRechazada(String correoDestino, String nombreTutor) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom("eliancamiloalejandro@gmail.com");
            helper.setTo("eliancamiloalejandro@gmail.com");
            helper.setSubject("Información sobre tu solicitud — Taller de Vida Universitaria");

            String htmlContent = """
            <!DOCTYPE html>
            <html lang="es">
            <body style="margin: 0; padding: 0; background-color: #f5f5f5; font-family: 'Segoe UI', Arial, sans-serif;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="padding: 40px 0;">
                    <tr><td align="center">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.08);">
                            <tr>
                                <td style="background-color: #7B0D1E; padding: 32px 40px; text-align: center;">
                                    <h1 style="margin: 8px 0 0 0; color: #ffffff; font-size: 28px; font-weight: 700; letter-spacing: 1px;">mi<span style="font-style: italic; font-weight: 300;">TVU</span></h1>
                                </td>
                            </tr>
                            <tr>
                                <td style="padding: 40px 40px 32px 40px;">
                                    <p style="margin: 0 0 16px 0; color: #1a1a1a; font-size: 16px;">Hola, <strong style="color: #7B0D1E;">%s</strong></p>
                                    <p style="color: #444444; font-size: 15px; line-height: 1.6;">Queremos agradecerte por tu interés en formar parte del equipo de tutores del Taller de Vida Universitaria.</p>
                                    <p style="color: #444444; font-size: 15px; line-height: 1.6;">Luego de evaluar tu perfil, lamentamos informarte que en esta ocasión no podremos avanzar con tu solicitud. Te invitamos a postularte nuevamente en futuras convocatorias.</p>
                                </td>
                            </tr>
                        </table>
                    </td></tr>
                </table>
            </body>
            </html>
            """.formatted(nombreTutor);

            helper.setText(htmlContent, true);
            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Error enviando correo SMTP", e);
        }
    }
}
