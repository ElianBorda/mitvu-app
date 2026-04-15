package com.unq.mitvu.controller;

import com.unq.mitvu.body.TutorBody;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.model.Tutor;
import com.unq.mitvu.service.ComisionService;
import com.unq.mitvu.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tutores")
@CrossOrigin(origins = "*")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private ComisionService comisionService;

    @GetMapping
    public ResponseEntity<List<TutorBody>> getAllTutores() {
        List<Tutor> tutores = tutorService.obtenerTodos();
        return new ResponseEntity<>(tutores.stream().map(TutorBody::fromTutor).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorBody> getTutorById(@PathVariable String id) {
        Tutor tutor = tutorService.obtenerPorId(id);
        return new ResponseEntity<>(TutorBody.fromTutor(tutor), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TutorBody> createTutor(@RequestBody TutorBody body) {
        Tutor tutor = body.toTutor();

        ArrayList<String> comisiones_ids = (ArrayList<String>) body.getComisiones_ids();
        tutor.setComisiones(comisionService.obtenerTodosPorId(comisiones_ids));

        Tutor tutorGuardado = tutorService.crear(tutor);
        comisionService.agregarTutorAComisiones(tutorGuardado, comisiones_ids);

        return new ResponseEntity<>(TutorBody.fromTutor(tutorGuardado), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutorBody> updateTutor(@PathVariable String id, @RequestBody TutorBody body) {
        Tutor tutor = tutorService.modificarPorId(id, body.toTutor());
        return new ResponseEntity<>(TutorBody.fromTutor(tutor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable String id) {
        tutorService.eliminarPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTutores() {
        tutorService.eliminarTodo();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
