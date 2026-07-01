package com.unq.mitvu.dao;

import com.unq.mitvu.model.Administrador;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Tutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorDAO extends MongoRepository<Tutor, String> {
    Tutor getById(String id);
    Optional<Tutor> findByDni(String dni);

}
