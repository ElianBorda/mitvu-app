package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.AnuncioBodyDTO;
import com.unq.mitvu.controller.dto.AnuncioDTO;
import com.unq.mitvu.mapper.AnuncioMapper;
import com.unq.mitvu.model.Anuncio;
import com.unq.mitvu.service.AnuncioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
@CrossOrigin(origins = "*")
public class AnuncioController {

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private AnuncioMapper anuncioMapper;

    @PostMapping
    public ResponseEntity<AnuncioDTO> crearAnuncio(@Valid @RequestBody AnuncioBodyDTO anuncioBodyDTO){
        Anuncio anuncio = anuncioMapper.aAnuncio(anuncioBodyDTO);
        Anuncio anuncioCreado = anuncioService.crear(anuncio);
        return new ResponseEntity<>(anuncioMapper.aAnuncioDTO(anuncioCreado), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AnuncioDTO>> obtenerTodosLosPublicos() {
        List<Anuncio> anuncios = anuncioService.obtenerTodosLosPublicos();
        return new ResponseEntity<>(anuncioMapper.aListaDeAnuncioDTO(anuncios),  HttpStatus.OK);
    }

    @GetMapping("/comision/{idComision}")
    public ResponseEntity<List<AnuncioDTO>> obtenerAnunciosDeComision(@PathVariable String idComision) {
        List<Anuncio> anuncios = anuncioService.obtenerAnunciosDeComision(idComision);
        return new ResponseEntity<>(anuncioMapper.aListaDeAnuncioDTO(anuncios),  HttpStatus.OK);
    }

}
