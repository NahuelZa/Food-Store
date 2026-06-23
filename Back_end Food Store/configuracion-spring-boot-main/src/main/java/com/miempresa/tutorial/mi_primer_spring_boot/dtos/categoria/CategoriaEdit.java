package com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;

public record CategoriaEdit(
        String nombre,
        String description
) {
    public void applyTo(Categoria categoria){
        if(this.nombre != null){
            categoria.setNombre(this.nombre);
        }
        if(this.description!= null){
            categoria.setDescription(this.description);
        }
    }
}
