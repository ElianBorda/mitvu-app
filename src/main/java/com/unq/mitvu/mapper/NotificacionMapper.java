package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.NotificacionBodyDTO;
import com.unq.mitvu.controller.dto.NotificacionDTO;
import com.unq.mitvu.model.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NotificacionMapper {
    NotificacionDTO aNotificacionDTO(Notificacion notificacion);
    List<NotificacionDTO> aListaNotificacionDTO(List<Notificacion> notificaciones);
    Notificacion aNotificacion(NotificacionBodyDTO notificacionBodyDTO);
}
