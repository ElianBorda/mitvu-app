package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "feedbacks")
public class FormularioFeedback {

    @Id
    private String id;
    private String tutorId;
    private String comisionId;
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