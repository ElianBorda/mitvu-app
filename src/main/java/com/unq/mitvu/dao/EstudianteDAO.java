package com.unq.mitvu.dao;

import com.unq.mitvu.model.Estudiante;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteDAO extends MongoRepository<Estudiante, String> {
    Estudiante getById(String id);
}
