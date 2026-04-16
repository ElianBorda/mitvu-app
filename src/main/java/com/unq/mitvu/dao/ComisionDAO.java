package com.unq.mitvu.dao;

import com.unq.mitvu.model.Comision;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ComisionDAO extends MongoRepository<Comision, String> {
    Comision getById(String id);
    List<Comision> findByLocalidadAndDepartamentoAndCarrera(String localidad, String departamento, String carrera);
    ArrayList<Comision> findByTutorIsEmpty();

    @Query("{ 'tutor': null }")
    List<Comision> findSinTutor();

    long countComisionsByDepartamentoAndLocalidadAndCarrera(String departamento, String localidad, String carrera);
}
