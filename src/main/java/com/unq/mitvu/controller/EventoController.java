package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.EventoBodyDTO;
import com.unq.mitvu.mapper.EventoMapper;
import com.unq.mitvu.model.Evento;
import com.unq.mitvu.model.Usuario;
import com.unq.mitvu.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoMapper eventoMapper;

    @PostMapping
    public ResponseEntity<Evento> crearEvento(@Valid @RequestBody EventoBodyDTO eventoBodyDTO) {

        return new ResponseEntity<>(new Evento(), HttpStatus.CREATED);
    }

}
