package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.EstudianteBodyDTO;
import com.unq.mitvu.controller.dto.detalle.EstudianteDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.EstudianteResumenDTO;
import com.unq.mitvu.mapper.ComisionMapper;
import com.unq.mitvu.mapper.EstudianteMapper;
import com.unq.mitvu.mapper.TutorMapper;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.EstudianteService;
import com.unq.mitvu.service.TutorService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired private TutorService tutorService;
    @Autowired private ComisionService comisionService;
    @Autowired private EstudianteService estudianteService;

    @Autowired private TutorMapper tutorMapper;
    @Autowired private ComisionMapper comisionMapper;
    @Autowired private EstudianteMapper estudianteMapper;

    @GetMapping
    public ResponseEntity<List<EstudianteDetalleDTO>> obtenerEstudiantes(){
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        List<EstudianteDetalleDTO> estudiantesDetalle = estudiantes.stream().map(e -> getEstudianteDetalleDTOResponseEntity(e).getBody()).toList();
        return ResponseEntity.ok(estudiantesDetalle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDetalleDTO> obtenerEstudiante(@PathVariable String id){
        Estudiante estudiante = estudianteService.obtenerPorId(id);
        return getEstudianteDetalleDTOResponseEntity(estudiante);
    }

    @PostMapping
    public ResponseEntity<EstudianteResumenDTO> crearEstudiante(@Valid @RequestBody EstudianteBodyDTO dto) {

        Estudiante estudiante = estudianteMapper.aEstudiante(dto);
        String comision_id = dto.getComision_id();

        if (comision_id != null && !comision_id.isEmpty()) {
            Comision comision = comisionService.obtenerPorId(comision_id);
            estudiante.setComision(comision);
        }

        Estudiante estudianteGuardado = estudianteService.crear(estudiante);
        return ResponseEntity
                .created(URI.create("/api/estudiantes/" + estudianteGuardado.getId()))
                .body(estudianteMapper.aEstudianteResumenDTO(estudianteGuardado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable String id) {
        estudianteService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{estudianteId}/asignarComision/{comisionId}")
    public ResponseEntity<EstudianteDetalleDTO> asignarEstudianteAComision(@PathVariable String estudianteId, @PathVariable String comisionId) {
        Estudiante estudiante = estudianteService.obtenerPorId(estudianteId);
        Comision comision = comisionService.obtenerPorId(comisionId);

        estudiante.setComision(comision);
        Estudiante estudianteConCom = estudianteService.modificarPorId(estudianteId, estudiante);

        return getEstudianteDetalleDTOResponseEntity(estudianteConCom);
    }

    @GetMapping("/comision/{idComision}")
    public ResponseEntity<List<EstudianteDetalleDTO>> obtenerEstudiantesDeComision(@PathVariable String idComision) {
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantesDeComision(idComision);
        List<EstudianteDetalleDTO> estudiantesDetalle = estudiantes.stream().map(e -> getEstudianteDetalleDTOResponseEntity(e).getBody()).toList();
        return ResponseEntity.ok(estudiantesDetalle);
    }

    @NonNull
    private ResponseEntity<EstudianteDetalleDTO> getEstudianteDetalleDTOResponseEntity(Estudiante estudiante) {
        EstudianteDetalleDTO estudianteDetalle =  estudianteMapper.aEstudianteDetalleDTO(estudiante);
        Comision comisionEstudiante = estudiante.getComision();
        if (comisionEstudiante != null && comisionEstudiante.getId() != null) {
            estudianteDetalle.setComision(comisionMapper.aComisionParaEstudianteDTO(comisionEstudiante));
        }
        estudianteDetalle.setRol(estudiante.getRol().getDescripcionRol());
        return ResponseEntity.ok(estudianteDetalle);
    }
}
