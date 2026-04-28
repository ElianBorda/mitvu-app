package com.unq.mitvu.dao;

import com.unq.mitvu.model.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoDAO extends MongoRepository<Evento, String> {
}
