package com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.DetallePedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Pedido;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Estado;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.FormaPago;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record PedidoDto(
        Long id,
        Estado estado,
        BigDecimal total,
        FormaPago formaPago,
        List<DetallePedidoDto> detalles,
        LocalDate fecha,
        Boolean activo

) {
    public static PedidoDto toDto(Pedido pedido){
        return new PedidoDto(
                pedido.getId(),
                pedido.getEstado(),
                pedido.getTotal(),
                pedido.getFormaPago(),
                pedido.getDetalles().stream()
                        .map(DetallePedidoDto::toDto)
                        .toList(),
                pedido.getFecha(),
                pedido.getActivo()

        );
    }
}
