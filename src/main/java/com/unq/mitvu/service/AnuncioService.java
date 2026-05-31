package com.unq.mitvu.service;

import com.unq.mitvu.model.Anuncio;

import java.util.List;

public interface AnuncioService {
    Anuncio crear(Anuncio anuncio);

    List<Anuncio> obtenerTodosLosPublicos();

    List<Anuncio> obtenerAnunciosDeComision(String idComision);
}
