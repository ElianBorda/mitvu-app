package com.unq.mitvu.controller;

import com.unq.mitvu.config.RabbitMQConfig;
import com.unq.mitvu.controller.body.AnuncioBodyDTO;
import com.unq.mitvu.controller.dto.AnuncioDTO;
import com.unq.mitvu.controller.dto.NotificacionAnuncioDTO;
import com.unq.mitvu.mapper.AnuncioMapper;
import com.unq.mitvu.model.Anuncio;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.service.AnuncioService;
import com.unq.mitvu.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<AnuncioDTO> crearAnuncio(@Valid @RequestBody AnuncioBodyDTO anuncioBodyDTO){
        Anuncio anuncio = anuncioMapper.aAnuncio(anuncioBodyDTO);
        Anuncio anuncioCreado = anuncioService.crear(anuncio);

        List<Estudiante> destinatarios;
        if (anuncioCreado.getIdComision() == null) {
            destinatarios = estudianteService.obtenerEstudiantesActivos();
        } else {
            destinatarios = estudianteService.obtenerEstudiantesDeComision(anuncioCreado.getIdComision());
        }

        destinatarios.forEach(estudiante -> {
            NotificacionAnuncioDTO dto = NotificacionAnuncioDTO.builder()
                    .idUsuario(estudiante.getId())
                    .fcmToken(estudiante.getFcmToken())
                    .nombreEstudiante(estudiante.getNombre())
                    .tituloAnuncio(anuncioCreado.getTitulo())
                    .descripcionAnuncio(anuncioCreado.getDescripcion())
                    .build();

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY_ANUNCIOS,
                    dto
            );
        });

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
