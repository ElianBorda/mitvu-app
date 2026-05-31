package com.unq.mitvu.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionAnuncioDTO {
    public String idUsuario;
    public String fcmToken;
    public String nombreEstudiante;
    public String tituloAnuncio;
    public String descripcionAnuncio;
}