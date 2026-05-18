package com.unq.mitvu.dao;

import com.unq.mitvu.model.Anuncio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AnuncioDAO extends MongoRepository<Anuncio, String> {

}
