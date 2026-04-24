package com.unq.mitvu.dao;

import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.MotivoBaja;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteDAO extends MongoRepository<Estudiante, String> {
    Estudiante getById(String id);

    @Query("{ 'comision' : ?0 }")
    List<Estudiante> findByComisionId(ObjectId idComision);

    @Query("{ 'comision.tutor.id' : ?0 }")
    List<Estudiante> findByComisionByTutorId(String idTutor);

    List<Estudiante> findByComisionIn(List<Comision> comisiones);

    List<Estudiante> findByComision_Tutor_Id(String comisionTutorId);

    Optional<Estudiante> findByIdAndComisionIsNotNull(String id);

    List<Estudiante> findByBaja_idComisionDadoDeBaja(String bajaIdComisionDadoDeBaja);

    List<Estudiante> findByBajaIsNotNull();

    List<Estudiante> findByBajaIsNull();

    Collection<Object> findByBaja_motivo(MotivoBaja bajaMotivo);

    Integer countEstudianteByBaja_Motivo(MotivoBaja bajaMotivo);

    Integer countEstudianteByBaja_idComisionDadoDeBaja(String bajaIdComisionDadoDeBaja);

    @Query(value = "{ $or: [ { 'comision': ObjectId(?0) }, { 'baja.idComisionAnterior': ObjectId(?0) } ] }", count = true)
    long contarTotalHistoricoDeComision(String idComision);

    Integer countEstudiantesByComision_IdAndBajaIsNull(String comisionId);

    Integer countEstudiantesByComision_IdAndBaja_Motivo(String comisionId, MotivoBaja bajaMotivo);

    Integer countByBajaIsNotNull();

    Integer countEstudianteByBajaIsNull();

    Integer countEstudiantesByBaja_idComisionDadoDeBajaAndBaja_Motivo(String bajaIdComisionDadoDeBaja, MotivoBaja bajaMotivo);

    Integer countEstudianteByComision_Id(String comisionId);
}
