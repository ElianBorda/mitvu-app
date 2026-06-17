package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.AdministradorBodyDTO;
import com.unq.mitvu.controller.dto.AdministradorDTO;
import com.unq.mitvu.model.Administrador;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AdministradorMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void actualizarAdministrador(Administrador datosNuevos, @MappingTarget Administrador entidadExistente);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Administrador aAdministrador(AdministradorBodyDTO administradorBodyDTO);

    AdministradorDTO aAdministradorDTO(Administrador administrador);


}
