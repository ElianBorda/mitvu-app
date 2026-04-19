package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.TutorBodyDTO;
import com.unq.mitvu.controller.dto.detalle.TutorDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.TutorResumenDTO;
import com.unq.mitvu.model.Tutor;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TutorMapper {
    void actualizarTutor(Tutor datosNuevos, @MappingTarget Tutor entidadExistente);

    Tutor aTutor(TutorBodyDTO tutorBodyDTO);

    @Named("mapeoResumen")
    TutorResumenDTO aTutorResumenDTO(Tutor tutor);

    @IterableMapping(qualifiedByName = "mapeoResumen")
    List<TutorResumenDTO>aListaDeTutorResumenDTO(List<Tutor> tutores);

    @Named("mapeoDetalle")
    @Mapping(target = "comisiones", ignore = true)
    TutorDetalleDTO aTutorDetalleDTO(Tutor tutor);
    List<TutorDetalleDTO> aListaDeTutorDetalleDTO(List<Tutor> tutores);
}
