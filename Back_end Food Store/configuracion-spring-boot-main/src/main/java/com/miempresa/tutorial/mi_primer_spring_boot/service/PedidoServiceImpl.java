package com.miempresa.tutorial.mi_primer_spring_boot.service;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.DetallePedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Pedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.DetallePedidoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.PedidoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.ProductoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Estado;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService{
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ProductoRepository productoRepository;


    //Sin uso
    @Override
    public void actualizarEstado(Long id, Estado estadoNuevo) {
       Pedido pedido=pedidoRepository.findById(id).orElseThrow(()->new NullPointerException("No se encontro pedido con id " + id));
       pedido.setEstado(estadoNuevo);
       pedidoRepository.save(pedido);

    }


    @Override
    @Transactional
    public PedidoDto findById(Long id) {
        Pedido pedido=pedidoRepository.findById(id).orElseThrow(()->new NullPointerException("No se encontro pedido con id " + id));
        if(!pedido.getActivo()){
            throw new IllegalArgumentException("Pedido no existe o eliminada de BD");
        }
        return PedidoDto.toDto(pedido);
    }


    @Override
    @Transactional
    public List<PedidoDto> findAll() {
        List<Pedido> pedidos=pedidoRepository.findAll();
        return pedidos.stream().filter(Pedido::getActivo).map(PedidoDto::toDto).toList();

    }

    /*
    {
        List <DetallePedido> detallePedidos = detallePedidoRepository.findAll();
        return detallePedidos.stream().filter(DetallePedido::getActivo).map(DetallePedidoDto::toDto).toList();
    }
     */

    @Override
    @Transactional
    public PedidoDto update(PedidoEdit pedidoEdit, Long idPedido) {
        Pedido pedido=pedidoRepository.findById(idPedido).orElseThrow(()->new NullPointerException("No se encontro pedido con id " + idPedido));
        DetallePedido detallePedido=null;
        Producto producto=null;

        if (pedidoEdit.idDetallesPedido() != null) {
            detallePedido = detallePedidoRepository.findById(pedidoEdit.idDetallesPedido())
                    .orElseThrow(() -> new RuntimeException("No se encontro detalle  con id " + pedidoEdit.idDetallesPedido()));

            if (pedidoEdit.idProducto() != null) {
                producto = productoRepository.findById(pedidoEdit.idProducto())
                        .orElseThrow(() -> new RuntimeException("No se encontro Producto con id " + pedidoEdit.idProducto()));
            }
        }
        pedidoEdit.applyTo(pedido,detallePedido,producto);
        pedido=pedidoRepository.save(pedido);
        return PedidoDto.toDto(pedido);
    }

    @Override
    public void deleteById(Long id) {
        Pedido pedido=pedidoRepository.findById(id).orElseThrow(()->new NullPointerException("No se encontro pedido con id " + id));
        if(!pedido.getActivo()){
            throw new IllegalArgumentException("Pedido no existe o eliminada de BD");
        }
        pedido.setActivo(false);
        pedidoRepository.save(pedido);


    }

    @Override
    @Transactional
    public List<PedidoDto> findByEstadoAndActivo(Estado estado) {
        List<Pedido> pedidos = pedidoRepository.findByEstadoAndActivo(estado,true);
        if (pedidos.isEmpty()) {
            throw new NullPointerException("No se encontraron pedidos activos con Estado " + estado);
        }
        return pedidos.stream().map(PedidoDto::toDto).toList();
    }
}
