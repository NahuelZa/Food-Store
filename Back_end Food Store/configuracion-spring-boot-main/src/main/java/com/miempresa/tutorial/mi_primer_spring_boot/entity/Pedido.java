package com.miempresa.tutorial.mi_primer_spring_boot.entity;


import com.miempresa.tutorial.mi_primer_spring_boot.enums.Estado;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.FormaPago;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Rol;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estado estado;
    private Boolean activo;

    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetallePedido> detalles = new ArrayList<>();

    private LocalDate fecha;


    public void addDetallePedido(int cantidad, Producto producto) {

        Optional<DetallePedido> detalleExistente = this.detalles.stream()
                .filter(d -> d.getProducto().getId().equals(producto.getId()))
                .findFirst();
        if (detalleExistente.isPresent()) {
            DetallePedido detalle = detalleExistente.get();
            int nuevaCantidad = detalle.getCantidad() + cantidad;
            detalle.setCantidad(nuevaCantidad);
            BigDecimal nuevoSubtotal = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(nuevaCantidad))
                    .setScale(2, RoundingMode.HALF_UP);
            detalle.setSubtotal(nuevoSubtotal);

        } else {
            DetallePedido nuevoDetalle = DetallePedido.builder()
                    .cantidad(cantidad)
                    .producto(producto)
                    .subtotal(
                            producto.getPrecio()
                                    .multiply(BigDecimal.valueOf(cantidad))
                                    .setScale(2, RoundingMode.HALF_UP)
                    )
                    .pedido(this)
                    .activo(true)
                    .build();


            this.detalles.add(nuevoDetalle);
        }

        calcularTotal();
    }


    public void calcularTotal() {
        this.total = detalles.stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }
        return null;
    }


}
