package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.SolicitudTutorBodyDTO;
import com.unq.mitvu.controller.dto.SolicitudTutorDTO;
import com.unq.mitvu.model.SolicitudTutor;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SolicitudTutorMapper {
    SolicitudTutor aSolicitudTutor(SolicitudTutorBodyDTO dto);
    SolicitudTutorDTO aSolicitudTutorDTO(SolicitudTutor modelo);
    List<SolicitudTutorDTO> aListaDeSolicitudesDTO(List<SolicitudTutor> modelos);
}