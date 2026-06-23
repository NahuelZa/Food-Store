package com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record ProductoCreate(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "El precio no puede ser nulo")
        @PositiveOrZero(message = "El precio debe ser mayor o igual a 0")
        BigDecimal precio,

        String descripcion,

        @PositiveOrZero(message = "El stock no puede ser negativo")
        int stock,

        String Imagen,
        Boolean disponible,

        @NotNull(message = "La categoría es obligatoria")
        Long idCategoria
) {
    public  Producto toEntity(Categoria categoria){
        return new Producto(this.nombre,this.precio,this.descripcion,this.stock,this.Imagen,categoria);

    }
}
