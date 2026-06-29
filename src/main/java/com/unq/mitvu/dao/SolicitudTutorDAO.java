package com.unq.mitvu.dao;

import com.unq.mitvu.model.EstadoSolicitud;
import com.unq.mitvu.model.SolicitudTutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudTutorDAO extends MongoRepository<SolicitudTutor, String> {
    // Custom query para que el admin vea solo las pendientes
    List<SolicitudTutor> findByEstadoSolicitud(EstadoSolicitud estado);
}