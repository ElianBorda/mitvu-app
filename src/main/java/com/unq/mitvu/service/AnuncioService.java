package com.unq.mitvu.service;

import com.unq.mitvu.model.Anuncio;

import java.util.List;

public interface AnuncioService {
    Anuncio crear(Anuncio anuncio);

    List<Anuncio> obtenerTodosLosPublicos();

    Anuncio obtenerPorId(String idAnuncio);

    List<Anuncio> obtenerAnunciosDeComision(String idComision);

    Anuncio modificarPorId(String idAnuncio, Anuncio anuncio);

    void eliminarAnuncio(String idAnuncio);

    void eliminarTodo();
}
