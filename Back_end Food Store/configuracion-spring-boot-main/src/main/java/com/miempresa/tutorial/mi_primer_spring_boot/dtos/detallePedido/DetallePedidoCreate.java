package com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.ArrayList;

@Builder
public record DetallePedidoCreate(
        int cantidad,
        BigDecimal subtotal,
        //relacion a producto
        Long idProducto,
        //relacion a pedido
        Long idPedido,
        Boolean activo
){
    public DetallePedido toEntity(Pedido pedido, Producto producto) {
        return DetallePedido.builder()
                .cantidad(this.cantidad)
                .subtotal(this.subtotal)
                .activo(this.activo)
                .pedido(pedido)
                .producto(producto)
                .build();
    }
}


