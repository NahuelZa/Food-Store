package com.miempresa.tutorial.mi_primer_spring_boot.entity.repository;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.pedido.PedidoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Pedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long > {

    @Query("SELECT p FROM Usuario u JOIN u.pedidos p WHERE u.id = :uid AND p.activo = true")
    List<Pedido> findPedidosByUsuarioYActivo(@Param("uid") Long usuarioId);


    List<Pedido> findByEstadoAndActivo(Estado estado, Boolean activo);
}


