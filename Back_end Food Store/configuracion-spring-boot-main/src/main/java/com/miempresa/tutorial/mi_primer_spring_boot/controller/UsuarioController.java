package com.miempresa.tutorial.mi_primer_spring_boot.controller;


import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuario")
@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> save(@Valid @RequestBody UsuarioCreate usuarioCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioCreate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> update(@Valid @RequestBody UsuarioEdit usuarioEdit, @Valid @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.update(usuarioEdit, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
