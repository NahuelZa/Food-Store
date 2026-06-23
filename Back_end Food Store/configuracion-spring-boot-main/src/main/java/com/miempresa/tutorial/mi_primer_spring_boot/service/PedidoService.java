package com.miempresa.tutorial.mi_primer_spring_boot.service;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Estado;

import java.util.List;

public interface PedidoService {
    public void actualizarEstado (Long id, Estado estado);
    public PedidoDto findById(Long id);
    public List<PedidoDto> findAll();
    public PedidoDto update (PedidoEdit pedidoEdit, Long idPedido);
    public void deleteById(Long id);
    public List<PedidoDto> findByEstadoAndActivo(Estado estado);
}