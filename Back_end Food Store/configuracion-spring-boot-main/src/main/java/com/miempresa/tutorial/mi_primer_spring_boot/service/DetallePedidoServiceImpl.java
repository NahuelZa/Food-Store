package com.miempresa.tutorial.mi_primer_spring_boot.service;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioDto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.DetallePedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Pedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Usuario;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.DetallePedidoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.PedidoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.ProductoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.UsuarioRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Estado;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.FormaPago;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;
    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

/*
Categoria categoria = categoriaRepository.findById(productoCreate.idCategoria()).orElseThrow(() -> new NullPointerException("No se encontro categoria"));
 */

    @Override
    @Transactional
    public DetallePedidoDto save(DetallePedidoCreate detallePedidoCreate, FormaPago formaPago, Long idUsuario) {

        Producto producto = productoRepository.findById(detallePedidoCreate.idProducto()).orElseThrow(() -> new NullPointerException("No se encontro el producto con id " + detallePedidoCreate.idProducto()));
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(()-> new NullPointerException("No se encontro Usuario "));

        Pedido pedido;
        if (detallePedidoCreate.idPedido() != null) {
            pedido = pedidoRepository.findById(detallePedidoCreate.idPedido()).orElseThrow(() -> new NullPointerException(("No se encontro pedido con id " + detallePedidoCreate.idPedido()))
            );
        } else {
            pedido =
                    Pedido.builder()
                            .estado(Estado.PENDIENTE)
                            .fecha(LocalDate.now())
                            .activo(true)
                            .formaPago(formaPago)
                            .build();
        }
        //Aca se "Crea" el detalle pedido en pedido
        pedido.addDetallePedido(detallePedidoCreate.cantidad(), producto);
        producto.setStock(producto.getStock()-detallePedidoCreate.cantidad());

        pedido = pedidoRepository.save(pedido);
        //Como esta en cascada al crear el pedido y asignarle el detalle pedido la base de datos lo crea automaticamente solo queda obtenerlo
        DetallePedido detallePedido =pedido.findDetallePedidoByProducto(producto);

        usuario.addPedido(pedido);
        usuarioRepository.save(usuario);

        return DetallePedidoDto.toDto(detallePedido);
    }

    @Override
    public DetallePedidoDto findById(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id).orElseThrow(() -> new NullPointerException("No se encontro detall pedido con id " + id));
        return DetallePedidoDto.toDto(detallePedido);
    }

    @Override
    public List<DetallePedidoDto> findAll() {
        List<DetallePedido> detallePedidos = detallePedidoRepository.findAll();
        return detallePedidos.stream().filter(DetallePedido::getActivo).map(DetallePedidoDto::toDto).toList();
    }


    @Override
    public DetallePedidoDto update(DetallePedidoDto detallePedidoDto, Long idDetallePedido) {

        return null;
    }


    @Override
    public void deleteById(Long id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id).orElseThrow(() -> new NullPointerException("No se encontro detalle pedido con id " + id));
        detallePedido.setActivo(false);
        detallePedidoRepository.save(detallePedido);
    }
    /*
    {
        Producto producto = productoRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro Producto con ID " + id));
        producto.setDisponible(false);
        productoRepository.save(producto);

    }
     */
}
