package com.miempresa.tutorial.mi_primer_spring_boot.controller;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/categoria")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaDto> save(@Valid @RequestBody CategoriaCreate categoriaCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoriaCreate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> update(@Valid @RequestBody CategoriaEdit categoriaEdit, @Valid @PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.update(categoriaEdit, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
