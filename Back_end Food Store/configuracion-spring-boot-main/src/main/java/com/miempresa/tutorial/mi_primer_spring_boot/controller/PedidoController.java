package com.miempresa.tutorial.mi_primer_spring_boot.controller;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoEdit;
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
@RequestMapping("/pedido")
@RequiredArgsConstructor

public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDto>> findAll() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> update(@Valid @RequestBody PedidoEdit pedidoEdit, @Valid @PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.update(pedidoEdit, id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
