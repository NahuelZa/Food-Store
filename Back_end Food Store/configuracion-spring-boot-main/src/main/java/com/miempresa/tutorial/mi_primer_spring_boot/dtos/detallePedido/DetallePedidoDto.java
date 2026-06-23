package com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.DetallePedido;

import java.math.BigDecimal;

public record DetallePedidoDto(
        Long id,
        int cantidad,
        BigDecimal subtotal,
        //relacion a producto
        ProductoDto productoDto,
        //relacion a pedido
        Long pedidoId,
        Boolean activo
) {

    public static DetallePedidoDto toDto(DetallePedido detallePedido){
        return new DetallePedidoDto(
                detallePedido.getId(),
                detallePedido.getCantidad(),
                detallePedido.getSubtotal(),
                ProductoDto.toDto(detallePedido.getProducto()),
                detallePedido.getPedido().getId(),
                detallePedido.getActivo()

        );
    }
}
