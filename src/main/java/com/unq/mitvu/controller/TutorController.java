package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.IDsBodyDTO;
import com.unq.mitvu.controller.body.TutorBodyDTO;
import com.unq.mitvu.controller.dto.detalle.ComisionParaTutorDTO;
import com.unq.mitvu.controller.dto.detalle.TutorDetalleDTO;
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
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tutores")
@CrossOrigin(origins = "*")
public class TutorController {

    @Autowired private TutorService tutorService;
    @Autowired private ComisionService comisionService;
    @Autowired private EstudianteService estudianteService;

    @Autowired private TutorMapper tutorMapper;
    @Autowired private ComisionMapper comisionMapper;
    @Autowired private EstudianteMapper estudianteMapper;

    @GetMapping
    public ResponseEntity<List<TutorDetalleDTO>> obtenerTutores() {
        List<Tutor> tutores = tutorService.obtenerTodos();
        List<TutorDetalleDTO> tutoresDetalle = tutores.stream().map(t -> getTutorDetalleDTOResponseEntity(t).getBody()).toList();
        return ResponseEntity.ok(tutoresDetalle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorDetalleDTO> obtenerTutor(@PathVariable String id){
        Tutor tutor = tutorService.obtenerPorId(id);
        return this.getTutorDetalleDTOResponseEntity(tutor);
    }

    @PostMapping
    public ResponseEntity<TutorResumenDTO> crearTutor(@Valid @RequestBody TutorBodyDTO tutorBodyDTO) {
        Tutor nuevoTutor = tutorMapper.aTutor(tutorBodyDTO);
        List<String> comisiones_ids = tutorBodyDTO.getComisiones_ids();

        Tutor tutorGuardado = tutorService.crear(nuevoTutor);

        if (comisiones_ids != null &&  !comisiones_ids.isEmpty()) {
            IDsBodyDTO idsBodyDTO = new IDsBodyDTO(comisiones_ids);
            agregarTutorAComisionesPorId(tutorGuardado.getId(), idsBodyDTO);
        }
        TutorResumenDTO response = tutorMapper.aTutorResumenDTO(tutorGuardado);
        return ResponseEntity.created(URI.create("/api/tutores/" + response.getId())).body(response);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> eliminarTutor(@PathVariable String id) {
        tutorService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/asignarComisionesATutor/{idTutor}"})
    public ResponseEntity<TutorDetalleDTO> agregarTutorAComisionesPorId(@PathVariable String idTutor, @RequestBody IDsBodyDTO idComisiones) {
        comisionService.agregarTutorAComisionesPorId(idTutor, idComisiones.getIds());
        Tutor tutor = tutorService.obtenerPorId(idTutor);
        return this.getTutorDetalleDTOResponseEntity(tutor);
    }

    @NotNull
    private ResponseEntity<TutorDetalleDTO> getTutorDetalleDTOResponseEntity(Tutor tutor) {
        List<Comision> comisiones = comisionService.obtenerComisionesDeTutor(tutor.getId());
        List<Estudiante> todosLosEstudiantes = estudianteService.obtenerTodosLosEstudiantesDeTutor(tutor.getId());
        Map<String, List<Estudiante>> estudiantesPorComision = todosLosEstudiantes.stream()
                .collect(Collectors.groupingBy(est -> est.getComision().getId()));
        List<ComisionParaTutorDTO> comisionParaTutorDTOS = comisiones.stream().map(comision -> {
            ComisionParaTutorDTO comisionParaTutorDTO = comisionMapper.aComisionParaTutorDTO(comision);
            List<Estudiante> estudiantesDeLaComision = estudiantesPorComision.getOrDefault(comision.getId(), new ArrayList<>());
            List<EstudianteResumenDTO> estudianteResumenDTOS = estudianteMapper.aListaDeEstudianteResumenDTO(estudiantesDeLaComision);
            comisionParaTutorDTO.setEstudiantes(estudianteResumenDTOS);
            return comisionParaTutorDTO;
        }).toList();
        TutorDetalleDTO tutorDetalle = tutorMapper.aTutorDetalleDTO(tutor);
        tutorDetalle.setComisiones(comisionParaTutorDTOS);
        tutorDetalle.setRol(tutor.getRol().getDescripcionRol());
        return ResponseEntity.ok(tutorDetalle);
    }
}
