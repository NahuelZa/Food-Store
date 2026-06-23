package com.miempresa.tutorial.mi_primer_spring_boot.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private int stock;
    private String Imagen;
    private Boolean disponible;

    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    public Producto(String nombre, BigDecimal precio, String descripcion, int stock, String imagen, Categoria categoria) {
        this(nombre, precio, descripcion, stock, imagen, categoria, true);
    }


    public Producto(String nombre, BigDecimal precio, String descripcion, int stock, String imagen, Categoria categoria, boolean disponible) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.Imagen = imagen;
        this.categoria = categoria;
        this.disponible = disponible;
    }
}
