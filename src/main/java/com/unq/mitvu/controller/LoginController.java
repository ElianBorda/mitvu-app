package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.LoginBodyDTO;
import com.unq.mitvu.controller.body.PrimerLogueoBodyDTO;
import com.unq.mitvu.controller.dto.LoginDTO;
import com.unq.mitvu.model.Usuario;
import com.unq.mitvu.service.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@Valid @RequestBody LoginBodyDTO loginBody) {

        Usuario usuario = loginService.autenticar(loginBody.getDni(), loginBody.getPassword());

        boolean requiereCambio = usuario.getRequiereCambioPassword() == null
                || usuario.getRequiereCambioPassword();

        LoginDTO respuesta = new LoginDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getDni(),
                usuario.getRol(),
                requiereCambio
        );

        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/primerLogueo")
    public ResponseEntity<Void> establecerPasswordDefinitiva(@Valid @RequestBody PrimerLogueoBodyDTO body) {

        loginService.registrarNuevaPassword(
                body.getDni(),
                body.getPasswordTemporal(),
                body.getPasswordNueva()
        );

        return ResponseEntity.noContent().build();
    }
}