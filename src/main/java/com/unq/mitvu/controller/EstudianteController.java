package com.unq.mitvu.controller;

import com.unq.mitvu.body.EstudianteBody;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private ComisionService comisionService;

    @PostMapping
    public ResponseEntity<EstudianteBody> createEstudiante(@RequestBody EstudianteBody body) {
        Estudiante estudiante = body.toEstudiante();

        estudiante.setComision(comisionService.obtenerPorId(body.getComision_id()));

        estudianteService.crear(estudiante);
        return new ResponseEntity<>(EstudianteBody.fromEstudiante(estudiante), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EstudianteBody>> getAllEstudiantes() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        List<EstudianteBody> estudiantesBody = estudiantes.stream().map(
                EstudianteBody::fromEstudiante
        ).toList();

        return new ResponseEntity<>(estudiantesBody, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteBody> getEstudianteById(@PathVariable String id) {
        Estudiante estudiante = estudianteService.obtenerPorId(id);
        return new ResponseEntity<>(EstudianteBody.fromEstudiante(estudiante), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteBody> updateEstudiante(@PathVariable String id, @RequestBody EstudianteBody body) {
        Estudiante estudiante = estudianteService.modificarPorId(id, body.toEstudiante());
        return new ResponseEntity<>(EstudianteBody.fromEstudiante(estudiante), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable String id) {
        estudianteService.eliminarPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllEstudiantes() {
        estudianteService.eliminarTodo();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
