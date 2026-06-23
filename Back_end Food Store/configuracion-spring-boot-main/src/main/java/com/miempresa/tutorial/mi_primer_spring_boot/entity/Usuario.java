package com.miempresa.tutorial.mi_primer_spring_boot.entity;

import com.miempresa.tutorial.mi_primer_spring_boot.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity



public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(
            name="usuario_id"
    )
    private List<Pedido> pedidos = new ArrayList<>();


    public void addPedido(Pedido p) { this.pedidos.add(p);}

}