package com.unq.mitvu.dao;

import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface EstudianteDAO extends MongoRepository<Estudiante, String> {
    Estudiante getById(String id);

    @Query("{ 'comision' : ?0 }")
    List<Estudiante> findByComisionId(ObjectId idComision);

    @Query("{ 'comision.tutor.id' : ?0 }")
    List<Estudiante> findByComisionByTutorId(String idTutor);

    List<Estudiante> findByComisionIn(List<Comision> comisiones);

    List<Estudiante> findByComision_Tutor_Id(String comisionTutorId);
}
