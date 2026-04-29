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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @Autowired
    private EventoMapper eventoMapper;

    @PostMapping
    public ResponseEntity<Evento> crearEventoAdmin(@Valid @RequestBody EventoBodyDTO eventoBodyDTO) {
        Evento evento = eventoMapper.aEvento(eventoBodyDTO);
        Evento nuevoEvento = eventoService.crear(evento);
        return new ResponseEntity<>(nuevoEvento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Evento>> obtenerEventos() {
        List<Evento> eventos = eventoService.obtenerTodos();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarEventos() {
        eventoService.eliminarTodo();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
