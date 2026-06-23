package com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Pedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Usuario;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Rol;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record UsuarioCreate(
        String nombre,
        String apellido,
        @Email(message = "Ingrese formato de email valido")
        String mail,
        String celular,
        String contrasena,
        Boolean activo,
        Rol rol
) {
    public UsuarioCreate {
        if (celular.trim().isEmpty()) {
            celular = null;
        }
    }

    public Usuario toEntity() {
        return Usuario.builder()
                .nombre(this.nombre)
                .apellido(this.apellido)
                .mail(this.mail)
                .celular(this.celular)
                .contrasena(this.contrasena)
                .activo(this.activo != null ? this.activo : true) // Si viene null, por defecto activo
                .rol(this.rol)
                .pedidos(new ArrayList<>()) // Nace con la lista de pedidos vacía y lista para usar
                .build();
    }
}
