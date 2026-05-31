package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.AnuncioBodyDTO;
import com.unq.mitvu.controller.dto.AnuncioDTO;
import com.unq.mitvu.model.Anuncio;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnuncioMapper {
    Anuncio aAnuncio(AnuncioBodyDTO anuncioBodyDTO);

    AnuncioDTO aAnuncioDTO(Anuncio anuncio);

    List<AnuncioDTO> aListaDeAnuncioDTO(List<Anuncio> anuncios);

}
