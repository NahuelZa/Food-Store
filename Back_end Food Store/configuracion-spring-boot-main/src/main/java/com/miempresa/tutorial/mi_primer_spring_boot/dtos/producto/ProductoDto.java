package com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;

import java.math.BigDecimal;

public record ProductoDto(
        Long id,
        String nombre,
        BigDecimal precio,
        String descripcion,
        int stock,
        String Imagen,
        Boolean disponible,
        CategoriaDto categoriaDto
) {

    public static ProductoDto toDto(Producto producto){
        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion(),
                producto.getStock(),
                producto.getImagen(),
                producto.getDisponible(),
                producto.getCategoria() != null ? CategoriaDto.toDto(producto.getCategoria()):null
        );
    }
}
