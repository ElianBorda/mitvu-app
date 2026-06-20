package com.unq.mitvu.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unq.mitvu.model.FrecuenciaEncuentro;
import com.unq.mitvu.model.RespuestaCerrada;
import com.unq.mitvu.model.UtilidadEncuentro;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FormularioFeedbackDTO {

    private String id;
    private String tutorId;
    private String comisionId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaEnvio;

    private Integer claridadYComunicacion;
    private Integer disponibilidadYRespuesta;
    private Integer tratoYEmpatia;
    private Integer puntajeGeneralTutor;

    private UtilidadEncuentro utilidadEncuentros;
    private FrecuenciaEncuentro frecuenciaYAsistencia;
    private RespuestaCerrada organizacion;

    private Integer acompanamientoInstitucional;
    private Boolean recomiendaEspacio;

    private String aspectosPositivos;
    private String oportunidadesMejora;
    private String comentariosAdicionales;
}