package com.unq.mitvu.mapper;

import com.unq.mitvu.controller.body.FormularioFeedbackBodyDTO;
import com.unq.mitvu.controller.dto.FormularioFeedbackDTO;
import com.unq.mitvu.model.FormularioFeedback;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormularioFeedbackMapper {

    FormularioFeedback aFormularioFeedback(FormularioFeedbackBodyDTO feedbackBodyDTO);

    FormularioFeedbackDTO aFormularioFeedbackDTO(FormularioFeedback feedback);

    List<FormularioFeedbackDTO> aListaDeFormularioFeedbackDTO(List<FormularioFeedback> feedbacks);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void actualizarFormularioFeedback(FormularioFeedback datosNuevos, @MappingTarget FormularioFeedback feedbackExistente);
}