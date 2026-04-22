package com.unq.mitvu.dao;

import com.unq.mitvu.model.Comision;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComisionDAO extends MongoRepository<Comision, String> {
    Comision getById(String id);
    List<Comision> findByLocalidadAndDepartamentoAndCarrera(String localidad, String departamento, String carrera);

    @Query("{ 'tutor': null }")
    List<Comision> findByTutorIsEmpty();


    long countComisionsByDepartamentoAndLocalidadAndCarrera(String departamento, String localidad, String carrera);

    @Query("{ 'tutor.id' : ?0 }")
    List<Comision> findByTutorId(String idTutor);

    Optional<Comision> findByIdAndTutorIsNotNull(String idComision);
}
