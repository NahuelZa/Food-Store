package com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario;

import com.miempresa.tutorial.mi_primer_spring_boot.entity.Pedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Usuario;
import com.miempresa.tutorial.mi_primer_spring_boot.enums.Rol;
import lombok.Builder;

@Builder
public record UsuarioEdit(
        String nombre,
        String apellido,
        String mail,
        String celular,
        String contrasena
) {
    public void applyTo(Usuario usuario) {

        if (this.nombre != null) {
            usuario.setNombre(this.nombre);
        }

        if (this.apellido != null) {
            usuario.setApellido(this.apellido);
        }

        if (this.mail != null) {
            usuario.setMail(this.mail);
        }

        if (this.celular != null) {
            usuario.setCelular(this.celular);
        }

        if (this.contrasena != null) {
            usuario.setContrasena(this.contrasena);
        }

    }
}