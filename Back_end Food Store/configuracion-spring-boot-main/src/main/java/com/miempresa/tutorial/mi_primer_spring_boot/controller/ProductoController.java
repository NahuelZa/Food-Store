package com.miempresa.tutorial.mi_primer_spring_boot.controller;


import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.service.CategoriaService;
import com.miempresa.tutorial.mi_primer_spring_boot.service.DetallePedidoService;
import com.miempresa.tutorial.mi_primer_spring_boot.service.PedidoService;
import com.miempresa.tutorial.mi_primer_spring_boot.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/producto")
@RequiredArgsConstructor

public class ProductoController {

    private final ProductoService productoService;


    @PostMapping
    public ResponseEntity<ProductoDto> save(@Valid @RequestBody ProductoCreate productoCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(productoCreate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> update(@Valid @RequestBody ProductoEdit productoEdit, @Valid @PathVariable Long id) {
        return ResponseEntity.ok(productoService.update(productoEdit, id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
