package com.unq.mitvu.controller;

import com.unq.mitvu.body.ComisionBody;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.service.ComisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comision")
@CrossOrigin(origins = "*")
public class ComisionController {

    @Autowired
    private ComisionService comisionService;

    @GetMapping("/{id}")
    public ResponseEntity<ComisionBody> getComisionById(@PathVariable String id) {
        Comision comision = comisionService.obtenerPorId(id);
        return new ResponseEntity<>(ComisionBody.fromComision(comision), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComisionBody> createComision(@RequestBody ComisionBody body) {
        Comision comision = comisionService.crear(body.toComision());
        return new ResponseEntity<>(ComisionBody.fromComision(comision), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComisionBody> updateComision(@PathVariable String id, @RequestBody ComisionBody body) {
        Comision comision = comisionService.modificarPorId(id, body.toComision());
        return new ResponseEntity<>(ComisionBody.fromComision(comision), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComision(@PathVariable String id){
        comisionService.eliminarPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ComisionBody>> getAllComisiones() {
        List<Comision> comisiones = comisionService.obtenerTodos();
        List<ComisionBody> comisionesBody = comisiones.stream().map(
                ComisionBody::fromComision
        ).toList();

        return new ResponseEntity<>(comisionesBody, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllComisiones() {
        comisionService.eliminarTodo();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
