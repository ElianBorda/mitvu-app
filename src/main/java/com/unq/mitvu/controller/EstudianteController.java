package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.BajaEstudianteBodyDTO;
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
import org.apache.coyote.Response;
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

    @PostMapping("/varios")
    public ResponseEntity<List<EstudianteResumenDTO>> crearEstudiantes(@Valid @RequestBody List<EstudianteBodyDTO> estudianteBodyDTOS) {
        List<Estudiante> estudiantes = estudianteBodyDTOS.stream().map(estudianteMapper::aEstudiante).toList();
        estudianteService.crearTodos(estudiantes);
        return ResponseEntity.ok(estudianteMapper.aListaDeEstudianteResumenDTO(estudiantes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable String id) {
        estudianteService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{estudianteId}/asignarComision/{comisionId}")
    public ResponseEntity<EstudianteDetalleDTO> asignarEstudianteAComision(@PathVariable String estudianteId, @PathVariable String comisionId) {
        return getEstudianteDetalleDTOResponseEntity(estudianteService.agregarEstudianteAComision(estudianteId, comisionId));
    }

    @GetMapping("/comision/{idComision}")
    public ResponseEntity<List<EstudianteDetalleDTO>> obtenerEstudiantesDeComision(@PathVariable String idComision) {
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantesDeComision(idComision);
        List<EstudianteDetalleDTO> estudiantesDetalle = estudiantes.stream().map(e -> getEstudianteDetalleDTOResponseEntity(e).getBody()).toList();
        return ResponseEntity.ok(estudiantesDetalle);
    }

    @PutMapping("/{idEstudiante}/baja")
    public ResponseEntity<EstudianteDetalleDTO> darDeBajaAEstudianteDeComision(@PathVariable String idEstudiante, @Valid @RequestBody BajaEstudianteBodyDTO bajaEstudianteBodyDTO){
        Estudiante estudiante = estudianteService.darseDeBaja(idEstudiante, estudianteMapper.aFormularioBaja(bajaEstudianteBodyDTO));
        return getEstudianteDetalleDTOResponseEntity(estudiante);
    }

    @GetMapping("/{idEstudiante}/dadoDeBaja")
    public boolean estaDadoDeBaja(@PathVariable String idEstudiante){
        return estudianteService.estaDadoDeBaja(idEstudiante);
    }

    @GetMapping("/baja")
    public ResponseEntity<List<EstudianteDetalleDTO>> obtenerEstudiantesDeBaja() {
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantesDeBaja();
        List<EstudianteDetalleDTO> estudiantesDetalle = estudiantes.stream().map(e -> getEstudianteDetalleDTOResponseEntity(e).getBody()).toList();
        return ResponseEntity.ok(estudiantesDetalle);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<EstudianteDetalleDTO>> obtenerEstudiantesActivos() {
        List<Estudiante> estudiantes = estudianteService.obtenerEstudiantesActivos();
        List<EstudianteDetalleDTO> estudiantesDetalle = estudiantes.stream().map(e -> getEstudianteDetalleDTOResponseEntity(e).getBody()).toList();
        return ResponseEntity.ok(estudiantesDetalle);
    }

    @GetMapping("comision/{idComision}/baja")
    public ResponseEntity<List<EstudianteResumenDTO>> obtenerTodosLosEstudiantesDadosDeBajaDeUnaComision(@PathVariable String idComision){
        List<Estudiante> estudiantes = estudianteService.obtenerTodosLosEstudiantesDadosDeBajaDeUnaComision(idComision);
        return ResponseEntity.ok(estudianteMapper.aListaDeEstudianteResumenDTO(estudiantes));
    }

    @NonNull
    private ResponseEntity<EstudianteDetalleDTO> getEstudianteDetalleDTOResponseEntity(Estudiante estudiante) {
        EstudianteDetalleDTO estudianteDetalle =  estudianteMapper.aEstudianteDetalleDTO(estudiante);
        Comision comisionEstudiante = estudiante.getComision();
        if (comisionEstudiante != null && comisionEstudiante.getId() != null) {
            estudianteDetalle.setComision(comisionMapper.aComisionParaEstudianteDTO(comisionEstudiante));
        }
        estudianteDetalle.setBaja(estudianteMapper.aBajaEstudianteDTO(estudiante.getBaja()));
        estudianteDetalle.setRol(estudiante.getRol().getDescripcionRol());
        return ResponseEntity.ok(estudianteDetalle);
    }
}
