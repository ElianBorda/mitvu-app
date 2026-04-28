package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.EventoBodyDTO;
import com.unq.mitvu.model.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventoMapper {
    void actualizarEvento(Evento datosNuevos, @MappingTarget Evento entidadExistente);

    Evento aEvento(EventoBodyDTO eventoBodyDTO);
}
