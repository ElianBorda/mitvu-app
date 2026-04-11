package com.unq.mitvu.controller;

import com.unq.mitvu.body.ComisionBody;
import com.unq.mitvu.model.Comision;
import com.unq.mitvu.service.ComisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comision")
@CrossOrigin(origins = "*")
public class ComisionController {

    @Autowired
    private ComisionService comisionService;

    @GetMapping("/{id}")
    public ComisionBody getComisionById(@PathVariable String id) {
        Comision comision = comisionService.obtenerPorId(id);
        return ComisionBody.fromComision(comision);
    }

    @PostMapping
    public ResponseEntity<ComisionBody> createComision(@RequestBody ComisionBody body) {
        Comision comision = comisionService.crear(body.toComision());
        return new ResponseEntity<>(ComisionBody.fromComision(comision), HttpStatus.CREATED);
    }
}
