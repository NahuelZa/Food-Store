package com.miempresa.tutorial.mi_primer_spring_boot.service;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.detallePedido.DetallePedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.FormaPago;

import java.util.List;

public interface DetallePedidoService {
    public DetallePedidoDto save (DetallePedidoCreate detallePedidoCreate, FormaPago formaPago,Long usuarioId);
    public DetallePedidoDto findById(Long id);
    public List<DetallePedidoDto> findAll();
    public DetallePedidoDto update (DetallePedidoDto detallePedido, Long idDetallePedido);
    public void deleteById(Long id);
}