package com.unq.mitvu.dao;

import com.unq.mitvu.model.Anuncio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AnuncioDAO extends MongoRepository<Anuncio, String> {

    List<Anuncio> findByidComision(String idComision);

    List<Anuncio> findByidComisionIsNull();
}
