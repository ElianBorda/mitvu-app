package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.EstudianteBodyDTO;
import com.unq.mitvu.controller.dto.detalle.ComisionParaEstudianteDTO;
import com.unq.mitvu.controller.dto.detalle.EstudianteDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.EstudianteResumenDTO;
import com.unq.mitvu.controller.dto.resumen.TutorResumenDTO;
import com.unq.mitvu.mapper.ComisionMapper;
import com.unq.mitvu.mapper.EstudianteMapper;
import com.unq.mitvu.mapper.TutorMapper;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.model.Tutor;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.EstudianteService;
import com.unq.mitvu.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<EstudianteResumenDTO>> obtenerEstudiantes(){
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        List<EstudianteResumenDTO> estudiantesResumen = estudianteMapper.aListaDeEstudianteResumenDTO(estudiantes);
        return ResponseEntity.ok(estudiantesResumen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDetalleDTO> obtenerEstudiante(@PathVariable String id){
        Estudiante estudiante = estudianteService.obtenerPorId(id);
        EstudianteDetalleDTO estudianteDetalle = estudianteMapper.aEstudianteDetalleDTO(estudiante);
        if (estudiante.getComision() != null && estudiante.getComision().getId() != null) {
            Comision comision = comisionService.obtenerPorId(estudiante.getComision().getId());
            ComisionParaEstudianteDTO comisionParaEstudianteDTO = comisionMapper.aComisionParaEstudianteDTO(comision);
            if (comision.getTutor() != null && comision.getTutor().getId() != null) {
                Tutor tutor = tutorService.obtenerPorId(comision.getTutor().getId());
                TutorResumenDTO tutorDTO = tutorMapper.aTutorResumenDTO(tutor);
                comisionParaEstudianteDTO.setTutor(tutorDTO);
            }
            estudianteDetalle.setComision(comisionParaEstudianteDTO);
        }
        estudianteDetalle.setRol(estudiante.getRol().getDescripcionRol());
        return ResponseEntity.ok(estudianteDetalle);
    }

    @PostMapping
    public ResponseEntity<EstudianteResumenDTO> crearEstudiante(@Valid @RequestBody EstudianteBodyDTO estudianteBodyDTO) {
        Estudiante nuevoEstudiante = estudianteMapper.aEstudiante(estudianteBodyDTO);
        Estudiante estudianteGuardado = estudianteService.crear(nuevoEstudiante);
        EstudianteResumenDTO response = estudianteMapper.aEstudianteResumenDTO(estudianteGuardado);
        return ResponseEntity.created(URI.create("/api/estudiantes/" + response.getId())).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable String id) {
        estudianteService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
