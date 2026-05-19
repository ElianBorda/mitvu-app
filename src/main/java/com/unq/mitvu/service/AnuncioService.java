package com.unq.mitvu.service;

import com.unq.mitvu.model.Anuncio;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AnuncioService {
    Anuncio crear(Anuncio anuncio);

    List<Anuncio> obtenerTodos();
}
