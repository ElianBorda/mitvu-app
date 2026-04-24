package com.unq.mitvu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MotivoBaja {
    FALTA_DE_TIEMPO("Falta de tiempo"),
    CAMBIO_INSTITUCION("Cambio de institución"),
    MOTIVOS_PERSONALES("Motivos personales"),
    PROBLEMAS_CURSADAS("Problemas con la cursada"),
    DISTANCIA_GEOGRAFICA("Distancia geografica"),
    FALTA_INFORMACION("Falta de información"),
    DESACUERDO_BUROCRACIA("Desacuerdo con la burocracia"),
    OTRO("Otro");

    private String descripciónMotivo;
}
