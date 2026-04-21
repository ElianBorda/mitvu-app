package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.ComisionBodyDTO;
import com.unq.mitvu.controller.dto.detalle.ComisionDetalleDTO;
import com.unq.mitvu.controller.dto.resumen.ComisionResumenDTO;
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
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/comisiones")
@CrossOrigin(origins = "*")
public class ComisionController {

    @Autowired private TutorService tutorService;
    @Autowired private ComisionService comisionService;
    @Autowired private EstudianteService estudianteService;

    @Autowired private TutorMapper tutorMapper;
    @Autowired private ComisionMapper comisionMapper;
    @Autowired private EstudianteMapper estudianteMapper;

    @GetMapping
    public ResponseEntity<List<ComisionDetalleDTO>> obtenerComisiones(){
        List<Comision> comisiones = comisionService.obtenerTodos();
        List<ComisionDetalleDTO> comisionesDetalle = comisiones.stream().map(c -> getComisionDetalleDTOResponseEntity(c).getBody()).toList();
        return ResponseEntity.ok(comisionesDetalle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComisionDetalleDTO> obtenerComision(@PathVariable String id){
        Comision comision = comisionService.obtenerPorId(id);
        return getComisionDetalleDTOResponseEntity(comision);
    }

    @PostMapping
    public ResponseEntity<ComisionResumenDTO> crearComision(@Valid @RequestBody ComisionBodyDTO comisionBodyDTO) {
        Comision nuevaComision = comisionMapper.aComision(comisionBodyDTO);
        Comision comisionGuardada = comisionService.crear(nuevaComision);
        ComisionResumenDTO response = comisionMapper.aComisionResumenDTO(comisionGuardada);
        return ResponseEntity.created(URI.create("/api/comisiones/" + response.getId())).body(response);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> eliminarComision(@PathVariable String id) {
        comisionService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComisionDetalleDTO> acutualizarComision(@Valid @RequestBody ComisionBodyDTO comisionBodyDTO, @PathVariable String id) {
        Comision comision = comisionService.modificarPorId(id, comisionMapper.aComision(comisionBodyDTO));
        return getComisionDetalleDTOResponseEntity(comision);
    }

    @PutMapping({"/agregarTutor/{idTutor}/{idComision}"})
    public ResponseEntity<ComisionDetalleDTO> agregarTutorAComision(@PathVariable String idTutor, @PathVariable String idComision) {
        Comision comision = comisionService.agregarTutorAComision(idTutor, idComision);
        return getComisionDetalleDTOResponseEntity(comision);
    }

    @PutMapping({"/cambiarTutor/{idTutor}/{idComision}"})
    public ResponseEntity<ComisionDetalleDTO> cambiarDeTutorEnComision(@PathVariable String idTutor, @PathVariable String idComision) {
        Comision comision = comisionService.cambiarDeTutorEnComision(idTutor, idComision);
        return getComisionDetalleDTOResponseEntity(comision);
    }

    @GetMapping({"/tutor/{idTutor}"})
    public ResponseEntity<List<ComisionResumenDTO>> obtenerComisionesDeTutor(@PathVariable String idTutor) {
        List<Comision> comisiones = comisionService.obtenerComisionesDeTutor(idTutor);
        return ResponseEntity.ok(comisionMapper.aListaDeComisionResumenDTO(comisiones));
    }

    @GetMapping({"/sinTutor"})
    public ResponseEntity<List<ComisionResumenDTO>> obtenerComisionesSinTutor() {
        List<Comision> comisiones = comisionService.obtenerComisionesSinTutor();
        return ResponseEntity.ok(comisionMapper.aListaDeComisionResumenDTO(comisiones));
    }

    @NonNull
    private ResponseEntity<ComisionDetalleDTO> getComisionDetalleDTOResponseEntity(Comision comision) {
        ComisionDetalleDTO comisionDetalle = comisionMapper.aComisionDetalleDTO(comision);
        if (comision.getTutor() != null && comision.getTutor().getId() != null) {
            Tutor tutor = tutorService.obtenerPorId(comision.getTutor().getId());
            TutorResumenDTO tutorDTO = tutorMapper.aTutorResumenDTO(tutor);
            comisionDetalle.setTutor(tutorDTO);
        }
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantesDeComision(comision.getId());
        List<EstudianteResumenDTO> estudiantesDTO = estudianteMapper.aListaDeEstudianteResumenDTO(estudiantes);
        comisionDetalle.setEstudiantes(estudiantesDTO);
        return ResponseEntity.ok(comisionDetalle);
    }


}
