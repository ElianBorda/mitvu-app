package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.ComisionBodyDTO;
import com.unq.mitvu.controller.dto.detalle.ComisionDetalleDTO;
import com.unq.mitvu.controller.dto.detalle.ComisionParaEstudianteDTO;
import com.unq.mitvu.controller.dto.detalle.ComisionParaTutorDTO;
import com.unq.mitvu.controller.dto.resumen.ComisionResumenDTO;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Horario;
import org.jspecify.annotations.Nullable;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComisionMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void actualizarComision(Comision datosNuevos, @MappingTarget Comision entidadExistente);

    Comision aComision(ComisionBodyDTO comisionBodyDTO);

    @Named("mapeoParaEstudiante")
    ComisionParaEstudianteDTO aComisionParaEstudianteDTO(Comision comision);
    @IterableMapping(qualifiedByName = "mapeoParaEstudiante")
    List<ComisionParaEstudianteDTO> aListaDeComisionParaEstudianteDTO(List<Comision> comisiones);

    @Named("mapeoParaTutor")
    ComisionParaTutorDTO aComisionParaTutorDTO(Comision comision);
    @IterableMapping(qualifiedByName = "mapeoParaTutor")
    List<ComisionParaTutorDTO> aListaDeComisionParaTutorDTO(List<Comision> comisiones);

    @Named("mapeoResumen")
    ComisionResumenDTO aComisionResumenDTO(Comision comision);
    @IterableMapping(qualifiedByName = "mapeoResumen")
    List<ComisionResumenDTO> aListaDeComisionResumenDTO(List<Comision> comisiones);

    @Named("mapeoDetalle")
    @Mapping(target = "estudiantes", ignore = true) // Lo llenamos en el controller
    @Mapping(target = "tutor") //, ignore = false)
    ComisionDetalleDTO aComisionDetalleDTO(Comision comision);
    @IterableMapping(qualifiedByName = "mapeoDetalle")
    @Nullable List<ComisionDetalleDTO> aListaDeComisionDetalleDTO(List<Comision> comisions);

    default Horario stringAHorario(String tiempo) {
        if (tiempo == null || tiempo.isEmpty()) {
            return null;
        }
        String[] partes = tiempo.split(":");
        Integer hora = Integer.parseInt(partes[0]);
        Integer minuto = Integer.parseInt(partes[1]);
        return new Horario(hora, minuto);
    }

    default String horarioAString(Horario horario) {
        if (horario == null) {
            return null;
        }
        return horario.toString();
    }
}
