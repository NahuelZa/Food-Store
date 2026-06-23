package com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CategoriaDto(
        @NotNull @NotBlank @Min(value = 1, message = "El código debe ser un número positivo mayor a 0")
        Long id,
        String nombre,
        String desciption
) {
    public static CategoriaDto toDto(Categoria categoria){
    return new CategoriaDto(categoria.getId(),categoria.getNombre(),categoria.getDescription());
    }
}
