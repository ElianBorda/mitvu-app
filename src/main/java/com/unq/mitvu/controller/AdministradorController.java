package com.unq.mitvu.controller;

import com.unq.mitvu.controller.body.AdministradorBodyDTO;
import com.unq.mitvu.controller.dto.AdministradorDTO;
import com.unq.mitvu.mapper.AdministradorMapper;
import com.unq.mitvu.model.Administrador;
import com.unq.mitvu.service.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/administradores")
@CrossOrigin(origins = "*")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private AdministradorMapper administradorMapper;

    @GetMapping
    public ResponseEntity<List<AdministradorDTO>> obtenerAdministradores() {
        List<Administrador> administradores = administradorService.obtenerTodos();
        List<AdministradorDTO> administradoresDTO = administradores.stream().map(a -> administradorMapper.aAdministradorDTO(a)).toList();

        return ResponseEntity.ok(administradoresDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministradorDTO> obtenerAdministrador(@PathVariable String id){
        Administrador administrador = administradorService.obtenerPorId(id);

        return ResponseEntity.ok(administradorMapper.aAdministradorDTO(administrador));
    }

    @PostMapping
    public ResponseEntity<AdministradorDTO> crearAdministrador(@Valid @RequestBody AdministradorBodyDTO administradorBodyDTO) {
        Administrador nuevoAdministrador = administradorMapper.aAdministrador(administradorBodyDTO);
        Administrador administradorCreado = administradorService.crear(nuevoAdministrador);

        AdministradorDTO response = administradorMapper.aAdministradorDTO(administradorCreado);

        return ResponseEntity.created(URI.create("/api/administradores/" + response.getId())).body(response);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> eliminarAdministrador(@PathVariable String id) {
        administradorService.eliminarPorId(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdministradorDTO> actualizarAdministrador(@RequestBody @Valid AdministradorBodyDTO administradorBodyDTO, @PathVariable String id) {
        Administrador administrador = administradorMapper.aAdministrador(administradorBodyDTO);
        Administrador administradorModificado = administradorService.modificarPorId(id, administrador);

        return ResponseEntity.ok(administradorMapper.aAdministradorDTO(administradorModificado));
    }

}
