package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.EstudianteBodyDTO;
import com.unq.mitvu.controller.dto.detalle.EstudianteDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.EstudianteResumenDTO;
import com.unq.mitvu.model.Estudiante;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void actualizarEstudiante(Estudiante datosNuevos, @MappingTarget Estudiante entidadExistente);

    Estudiante aEstudiante(EstudianteBodyDTO estudiantebodyDTO);

    @Named("mapeoResumen")
    EstudianteResumenDTO aEstudianteResumenDTO(Estudiante estudiante);

    @IterableMapping(qualifiedByName = "mapeoResumen")
    List<EstudianteResumenDTO> aListaDeEstudianteResumenDTO(List<Estudiante> estudiantes);

    @Named("mapeoDetalle")
    @Mapping(target = "comision", ignore = true) // Lo llenamos en el controller
    EstudianteDetalleDTO aEstudianteDetalleDTO(Estudiante estudiante);
}
