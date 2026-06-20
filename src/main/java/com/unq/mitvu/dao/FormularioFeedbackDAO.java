package com.unq.mitvu.dao;

import com.unq.mitvu.model.FormularioFeedback;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormularioFeedbackDAO extends MongoRepository<FormularioFeedback, String> {
}