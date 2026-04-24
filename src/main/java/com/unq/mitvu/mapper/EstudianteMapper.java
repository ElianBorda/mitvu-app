package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.BajaEstudianteBodyDTO;
import com.unq.mitvu.controller.body.EstudianteBodyDTO;
import com.unq.mitvu.controller.dto.BajaEstudianteDTO;
import com.unq.mitvu.controller.dto.detalle.EstudianteDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.EstudianteResumenDTO;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.FormularioBaja;
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
    @Mapping(target = "comision", ignore = true)
    EstudianteDetalleDTO aEstudianteDetalleDTO(Estudiante estudiante);

    @Mapping(target = "fechaBaja", expression = "java(java.time.LocalDate.now())")
    FormularioBaja aFormularioBaja(BajaEstudianteBodyDTO bajaEstudianteBodyDTO);

    BajaEstudianteDTO aBajaEstudianteDTO(FormularioBaja formularioBaja);
}
