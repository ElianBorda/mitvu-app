package com.unq.mitvu.dao;

import com.unq.mitvu.model.Comision;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComisionDAO extends MongoRepository<Comision, String> {
    Comision getById(String id);
}
