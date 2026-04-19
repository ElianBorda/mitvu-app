package com.unq.mitvu.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Asistencia {
    @NonNull private LocalDate fecha;
    @NonNull private Boolean asistio;
    private String observacion;
}
