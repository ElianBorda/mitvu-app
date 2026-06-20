package com.unq.mitvu.service;

import com.unq.mitvu.model.FormularioFeedback;

import java.util.List;

public interface FormularioFeedbackService {
    FormularioFeedback guardarFeedback(FormularioFeedback feedback);
    List<FormularioFeedback> obtenerTodosLosFeedbacks();
}