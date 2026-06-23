package com.miempresa.tutorial.mi_primer_spring_boot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter



@Entity

public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String nombre;
    private String description;
    private Boolean eliminado;


    public Categoria(String nombre, String description) {
        this.nombre = nombre;
        this.description = description;
        this.eliminado=false;
    }

}
