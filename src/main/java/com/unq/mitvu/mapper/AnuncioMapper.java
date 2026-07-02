package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.AnuncioBodyDTO;
import com.unq.mitvu.controller.dto.AnuncioDTO;
import com.unq.mitvu.model.Anuncio;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnuncioMapper {
    Anuncio aAnuncio(AnuncioBodyDTO anuncioBodyDTO);

    AnuncioDTO aAnuncioDTO(Anuncio anuncio);

    List<AnuncioDTO> aListaDeAnuncioDTO(List<Anuncio> anuncios);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void actualizarAnuncio(Anuncio datosNuevos, @MappingTarget Anuncio anuncioExistente);
}
