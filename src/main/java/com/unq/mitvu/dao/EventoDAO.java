package com.unq.mitvu.dao;

import com.unq.mitvu.model.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoDAO extends MongoRepository<Evento, String> {

    @Query("{ $or: [ { 'idComision': ?0 }, { 'esGlobal': true } ] }")
    List<Evento> findByComisionOCentral(String idComision);
}
