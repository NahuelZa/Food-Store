package com.miempresa.tutorial.mi_primer_spring_boot.controller;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.DetallePedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.DetallePedidoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.PedidoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.ProductoRepository;
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
@RequestMapping("/detallePedido")
@RequiredArgsConstructor

public class DetallePedidoController {

    private final CategoriaService categoriaService;
    private final DetallePedidoService detallePedidoService;
    private final ProductoService productoService;
    private final PedidoService pedidoService;

    @PostMapping
    //public ResponseEntity<DetallePedidoDto> save(@Valid @RequestBody DetallePedidoCreate detallePedidoCreate) {
        //return ResponseEntity.status(HttpStatus.CREATED).body(detallePedidoService.save(detallePedidoCreate));
   // }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(detallePedidoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<DetallePedidoDto>> findAll() {
        return ResponseEntity.ok(detallePedidoService.findAll());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Valid Long id) {
        detallePedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}




