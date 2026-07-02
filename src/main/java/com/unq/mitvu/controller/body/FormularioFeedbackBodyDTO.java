package com.unq.mitvu.controller.body;

import com.unq.mitvu.model.FrecuenciaEncuentro;
import com.unq.mitvu.model.RespuestaCerrada;
import com.unq.mitvu.model.UtilidadEncuentro;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FormularioFeedbackBodyDTO {
    @NotBlank(message = "El ID del tutor es obligatorio")
    private String tutorId;
    @NotBlank(message = "El ID de la comisión es obligatorio")
    private String comisionId;

    @NotNull(message = "La claridad y comunicación es obligatoria")
    @Min(value = 1, message = "El valor mínimo es 1")
    @Max(value = 5, message = "El valor máximo es 5")
    private Integer claridadYComunicacion;

    @NotNull(message = "La disponibilidad y respuesta es obligatoria")
    @Min(value = 1, message = "El valor mínimo es 1")
    @Max(value = 5, message = "El valor máximo es 5")
    private Integer disponibilidadYRespuesta;

    @NotNull(message = "El trato y empatía es obligatorio")
    @Min(value = 1, message = "El valor mínimo es 1")
    @Max(value = 5, message = "El valor máximo es 5")
    private Integer tratoYEmpatia;

    @NotNull(message = "El puntaje general del tutor es obligatorio")
    @Min(value = 1, message = "El valor mínimo es 1")
    @Max(value = 5, message = "El valor máximo es 5")
    private Integer puntajeGeneralTutor;

    @NotNull(message = "La utilidad de los encuentros es obligatoria")
    private UtilidadEncuentro utilidadEncuentros;

    @NotNull(message = "La frecuencia y asistencia es obligatoria")
    private FrecuenciaEncuentro frecuenciaYAsistencia;

    @NotNull(message = "La organización es obligatoria")
    private RespuestaCerrada organizacion;

    @NotNull(message = "Debe responder sobre el acompañamiento institucional")
    @Min(value = 1, message = "El valor mínimo es 1")
    @Max(value = 5, message = "El valor máximo es 5")
    private Integer acompanamientoInstitucional;

    @NotNull(message = "Debe indicar si recomienda el espacio")
    private Boolean recomiendaEspacio;

    @Size(max = 1000, message = "Los aspectos positivos no pueden superar los 1000 caracteres")
    private String aspectosPositivos;

    @Size(max = 1000, message = "Las oportunidades de mejora no pueden superar los 1000 caracteres")
    private String oportunidadesMejora;

    @Size(max = 1500, message = "Los comentarios adicionales no pueden superar los 1500 caracteres")
    private String comentariosAdicionales;
}