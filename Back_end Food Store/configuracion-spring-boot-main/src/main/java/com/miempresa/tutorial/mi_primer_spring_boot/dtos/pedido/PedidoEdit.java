package com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.DetallePedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Pedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Estado;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.FormaPago;
import com.miempresa.tutorial.mi_primer_spring_boot.service.ProductoServiceImpl;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Builder
public record PedidoEdit(
        Estado estado,
        BigDecimal total,
        FormaPago formaPago,
        //id del detallePedido que quiero cambiar
        Long idDetallesPedido,
        //Los cambios que quiero realizar en detalle pedido
        Integer cantidad,
        Long idProducto,

        LocalDate fecha,
        Boolean activo


) {
    public void applyTo(Pedido pedido, DetallePedido detallePedido,Producto producto) {
        if (this.estado != null) {
            pedido.setEstado(this.estado);
        }

        if (this.total != null) {
            pedido.setTotal(this.total);
        }

        if (this.formaPago != null) {
            pedido.setFormaPago(this.formaPago);
        }

        if (this.fecha != null) {
            pedido.setFecha(this.fecha);
        }

        if (this.activo != null) {
            pedido.setActivo(this.activo);
        }

        if (detallePedido != null) {
            pedido.getDetalles().stream()
                    .filter(d -> d.getId().equals(this.idDetallesPedido))
                    .forEach(detalleExistente -> {
                        if(this.cantidad!=null){
                            detalleExistente.setCantidad(this.cantidad);
                        }
                        if(producto!=null){
                            detalleExistente.setProducto(producto);
                        }


                    });

        }
    }
}
