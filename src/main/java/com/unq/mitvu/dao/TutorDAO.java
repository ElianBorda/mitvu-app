package com.unq.mitvu.dao;

import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Tutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorDAO extends MongoRepository<Tutor, String> {
    Tutor getById(String id);
}
