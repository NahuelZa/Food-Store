package com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Usuario;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Rol;

import java.util.List;


public record UsuarioDto(
        Long id,
        String nombre,
        String apellido,
        String mail,
        String celular,
        Boolean activo,
        Rol rol,
        List<PedidoDto> pedidos
) {
    public static UsuarioDto toDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getMail(),
                usuario.getCelular(),
                usuario.getActivo(),
                usuario.getRol(),
                //Se fija si pedidos del usario son null si son null crea lista vacia
                usuario.getPedidos() != null
                        ? usuario.getPedidos().stream().map(PedidoDto::toDto).toList()
                        : List.of()
        );
    }
}
/*
public static ProductoDto toDto(Producto producto){
        return new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getDescripcion(),
                producto.getStock(),
                producto.getImagen(),
                producto.getDisponible(),
                producto.getCategoria() != null ? CategoriaDto.toDto(producto.getCategoria()):null
        );
    }
 */