package com.unq.mitvu.controller;

import com.unq.mitvu.body.TutorBody;
import com.unq.mitvu.model.Tutor;
import com.unq.mitvu.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tutores")
@CrossOrigin(origins = "*")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    public Tutor crear(@RequestBody TutorBody tutorBody) {
        Tutor tutor = tutorBody.toTutor();
        tutorService.crear(tutor);
        return tutor;
    }

}
