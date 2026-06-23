package com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;

import java.math.BigDecimal;

public record ProductoEdit(
        String nombre,
        BigDecimal precio,
        String descripcion,
        Integer stock,
        String Imagen,
        Boolean disponible,

        //relacion a categoria
        Long idCategoria
) {
    public void applyTo(Producto producto, Categoria categoria) {
        if (this.nombre != null) {
            producto.setNombre(this.nombre);
        }

        if (this.precio != null) {
            producto.setPrecio(this.precio);
        }

        if (this.descripcion != null) {
            producto.setDescripcion(this.descripcion);
        }

        if (this.stock != null) {
            producto.setStock(this.stock);
        }

        if (this.Imagen != null) {
            producto.setImagen(this.Imagen);
        }

        if (this.disponible != null) {
            producto.setDisponible(this.disponible);
        }

        if (categoria!=null) {
            producto.setCategoria(categoria);
        }
        else {
            producto.setCategoria(null);
        }
    }
}

