package com.unq.mitvu.controller;

import com.unq.mitvu.body.EstudianteBody;
import com.unq.mitvu.model.Estudiante;
import com.unq.mitvu.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping
    public Estudiante crear(@RequestBody EstudianteBody estudianteBody){
        Estudiante estudiante = estudianteBody.toEstudiante();
        return estudianteService.crear(estudiante);
    }

}
