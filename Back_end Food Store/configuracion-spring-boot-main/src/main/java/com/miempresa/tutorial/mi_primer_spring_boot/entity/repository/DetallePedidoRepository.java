package com.miempresa.tutorial.mi_primer_spring_boot.entity.repository;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Long > {
}
