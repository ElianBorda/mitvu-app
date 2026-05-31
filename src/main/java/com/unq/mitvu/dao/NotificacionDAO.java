package com.unq.mitvu.dao;

import com.unq.mitvu.model.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionDAO extends MongoRepository<Notificacion, String> {
    List<Notificacion> findByIdUsuario(String idUsuario);
}