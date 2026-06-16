package com.unq.mitvu.service;

import com.unq.mitvu.dao.AnuncioDAO;
import com.unq.mitvu.dao.ComisionDAO;
import com.unq.mitvu.dao.TutorDAO;
import com.unq.mitvu.exceptions.ReglaDeNegocioException;
import com.unq.mitvu.mapper.AnuncioMapper;
import com.unq.mitvu.model.Anuncio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class AnuncioServiceImpl implements AnuncioService {

    @Autowired
    private AnuncioMapper anuncioMapper;

    private TutorDAO tutorDAO;
    private ComisionDAO comisionDAO;
    private AnuncioDAO anuncioDAO;

    @Override
    public Anuncio crear(Anuncio anuncio) {
        anuncio.setFechaDeCreacion(LocalDate.now());
        if (anuncio.getIdComision() != null) {
            comisionDAO.findById(anuncio.getIdComision()).orElseThrow(
                    () -> new ReglaDeNegocioException("No se puede asignar el evento a la COMISIÓN con id: " + anuncio.getIdComision() + " porque no existe")
            );
        }
        if (anuncio.getCreadoPorId() != null) {
            tutorDAO.findById(anuncio.getCreadoPorId()).orElseThrow(
                    () -> new ReglaDeNegocioException("No se puede asignar como creador al tutor con ID: " + anuncio.getCreadoPorId() + "porque no existe")
            );
        }
        else {
            anuncio.setCreadoPorId("Administrador");
        }
        return anuncioDAO.save(anuncio);
    }

    @Override
    public List<Anuncio> obtenerTodosLosPublicos() {

        return anuncioDAO.findByidComisionIsNull();

    }

    @Override
    public Anuncio obtenerPorId(String idAnuncio) {
        Anuncio anuncio = anuncioDAO.findById(idAnuncio).orElseThrow(
                () -> new ReglaDeNegocioException("No existe el anuncio con id: " + idAnuncio)
        );

        return anuncio;
    }

    @Override
    public List<Anuncio> obtenerAnunciosDeComision(String idComision) {

        comisionDAO.findById(idComision).orElseThrow(
                () -> new ReglaDeNegocioException("No se puede asignar el evento a la COMISIÓN con id: " + idComision + " porque no existe")
        );

        return anuncioDAO.findByidComision(idComision);
    }

    @Override
    public Anuncio modificarPorId(String idAnuncio, Anuncio anuncio) {
        Anuncio anuncioEncontrado = this.obtenerPorId(idAnuncio);
        anuncioMapper.actualizarAnuncio(anuncio, anuncioEncontrado);
        return anuncioDAO.save(anuncioEncontrado);
    }

    @Override
    public void eliminarAnuncio(String idAnuncio) {
        anuncioDAO.delete(this.obtenerPorId(idAnuncio));
    }
}
