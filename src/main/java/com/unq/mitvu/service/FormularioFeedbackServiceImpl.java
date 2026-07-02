package com.unq.mitvu.service;

import com.unq.mitvu.dao.FormularioFeedbackDAO;
import com.unq.mitvu.model.FormularioFeedback;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FormularioFeedbackServiceImpl implements FormularioFeedbackService {

    private final FormularioFeedbackDAO formularioFeedbackDAO;

    private final ComisionService comisionService;
    private final TutorService tutorService;

    @Override
    public FormularioFeedback guardarFeedback(FormularioFeedback feedback) {

        tutorService.obtenerPorId(feedback.getTutorId());
        comisionService.obtenerPorId(feedback.getComisionId());

        feedback.setFechaEnvio(LocalDateTime.now());

        return formularioFeedbackDAO.save(feedback);
    }

    @Override
    public List<FormularioFeedback> obtenerTodosLosFeedbacks() {
        return formularioFeedbackDAO.findAll();
    }

    @Override
    public void eliminarTodo() {
        formularioFeedbackDAO.deleteAll();
    }
}