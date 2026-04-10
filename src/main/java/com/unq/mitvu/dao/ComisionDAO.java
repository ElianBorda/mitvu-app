package com.unq.mitvu.dao;

import com.unq.mitvu.model.Comision;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComisionDAO extends MongoRepository<Comision, String> {
    Comision getById(String id);
    List<Comision> findByLocalidadAndDepartamentoAndCarrera(String localidad, String departamento, String carrera);
}
