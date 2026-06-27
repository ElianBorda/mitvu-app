package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.SolicitudTutorBodyDTO;
import com.unq.mitvu.controller.dto.SolicitudTutorDTO;
import com.unq.mitvu.controller.dto.resumen.TutorResumenDTO;
import com.unq.mitvu.mapper.SolicitudTutorMapper;
import com.unq.mitvu.mapper.TutorMapper;
import com.unq.mitvu.model.SolicitudTutor;
import com.unq.mitvu.model.Tutor;
import com.unq.mitvu.service.SolicitudTutorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudesTutores")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class SolicitudTutorController {

    private final SolicitudTutorService solicitudService;
    private final SolicitudTutorMapper solicitudMapper;
    private final TutorMapper tutorMapper;

    @PostMapping
    public ResponseEntity<SolicitudTutorDTO> crearSolicitud(@Valid @RequestBody SolicitudTutorBodyDTO dto) {
        SolicitudTutor modelo = solicitudMapper.aSolicitudTutor(dto);
        SolicitudTutor guardado = solicitudService.crearSolicitud(modelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitudMapper.aSolicitudTutorDTO(guardado));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudTutorDTO>> listarPendientes() {
        List<SolicitudTutor> pendientes = solicitudService.obtenerPendientes();
        return ResponseEntity.ok(solicitudMapper.aListaDeSolicitudesDTO(pendientes));
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<TutorResumenDTO> aprobarSolicitud(@PathVariable String id) {
        Tutor tutorCreado = solicitudService.aprobarSolicitud(id);
        return ResponseEntity.ok(tutorMapper.aTutorResumenDTO(tutorCreado));
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<Void> rechazarSolicitud(@PathVariable String id) {
        solicitudService.rechazarSolicitud(id);
        return ResponseEntity.noContent().build();
    }
}