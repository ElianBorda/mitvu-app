package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.EventoBodyDTO;
import com.unq.mitvu.controller.dto.EventoDTO;
import com.unq.mitvu.model.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventoMapper {
    void actualizarEvento(Evento datosNuevos, @MappingTarget Evento entidadExistente);

    Evento aEvento(EventoBodyDTO eventoBodyDTO);

    EventoDTO aEventoDTO(Evento evento);

    List<EventoDTO> aListaDeEventoDTO(List<Evento> eventos);
}
