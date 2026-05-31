package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.NotificacionBodyDTO;
import com.unq.mitvu.controller.dto.NotificacionDTO;
import com.unq.mitvu.mapper.NotificacionMapper;
import com.unq.mitvu.model.Notificacion;
import com.unq.mitvu.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionMapper notificacionMapper;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionDTO>> obtenerPorUsuario(@PathVariable String idUsuario) {
        return ResponseEntity.ok(notificacionMapper.aListaNotificacionDTO(notificacionService.obtenerPorUsuario(idUsuario)));
    }

    @PostMapping()
    public ResponseEntity<NotificacionDTO> crearNotificacion(@RequestBody @Valid NotificacionBodyDTO notificacionBodyDTO){
        Notificacion notificacion = notificacionService.crear(notificacionMapper.aNotificacion(notificacionBodyDTO));
        NotificacionDTO notificacionDTO = notificacionMapper.aNotificacionDTO(notificacion);
        return ResponseEntity.created(URI.create("/api/notificaciones/" + notificacionDTO.getId())).body(notificacionDTO);
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable String id) {
        return ResponseEntity.ok(notificacionMapper.aNotificacionDTO(notificacionService.marcarNotificacionComoLeida(id)));
    }
}
