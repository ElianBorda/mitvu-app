package com.unq.mitvu.dao;

import com.unq.mitvu.model.Administrador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorDAO extends MongoRepository<Administrador, String> {
    Administrador getById(String id);
    Optional<Administrador> findByDni(String dni);
}
