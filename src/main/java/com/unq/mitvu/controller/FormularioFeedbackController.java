package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.FormularioFeedbackBodyDTO;
import com.unq.mitvu.controller.dto.FormularioFeedbackDTO;
import com.unq.mitvu.mapper.FormularioFeedbackMapper;
import com.unq.mitvu.model.FormularioFeedback;
import com.unq.mitvu.service.FormularioFeedbackService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class FormularioFeedbackController {

    private final FormularioFeedbackService feedbackService;
    private final FormularioFeedbackMapper feedbackMapper;

    @PostMapping
    public ResponseEntity<FormularioFeedbackDTO> enviarFeedback(@Valid @RequestBody FormularioFeedbackBodyDTO feedbackBodyDTO) {
        FormularioFeedback feedbackModelo = feedbackMapper.aFormularioFeedback(feedbackBodyDTO);
        FormularioFeedback feedbackGuardado = feedbackService.guardarFeedback(feedbackModelo);
        FormularioFeedbackDTO respuestaDTO = feedbackMapper.aFormularioFeedbackDTO(feedbackGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDTO);
    }

    @GetMapping
    public ResponseEntity<List<FormularioFeedbackDTO>> obtenerTodosLosFeedbacks() {
        List<FormularioFeedback> feedbacks = feedbackService.obtenerTodosLosFeedbacks();
        List<FormularioFeedbackDTO> respuestaDTO = feedbackMapper.aListaDeFormularioFeedbackDTO(feedbacks);
        return ResponseEntity.ok(respuestaDTO);
    }
}