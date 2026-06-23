package com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoriaCreate(
        @NotNull(message = "Categoria no puede ser null")
        @NotBlank(message = "Categoria no puede estar vacio")
        String nombre,
        String description
) {
    public Categoria toEntity(){
        return new Categoria(this.nombre,this.description);
    }
}
