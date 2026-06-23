package com.miempresa.tutorial.mi_primer_spring_boot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "pedido")

@Entity

public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private int cantidad;
    private BigDecimal subtotal;
    @ManyToOne
    @JoinColumn(name = "producto_id" )
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    private Boolean activo;


}
