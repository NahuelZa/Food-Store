package com.miempresa.tutorial.mi_primer_spring_boot.service;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Pedido;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Usuario;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.PedidoRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService{
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    public UsuarioDto save(UsuarioCreate usuarioCreate) {
        if (usuarioRepository.existsByMail(usuarioCreate.mail())) {
            throw new IllegalArgumentException("Ya existe un email con dirección " + usuarioCreate.mail());
        }
        Usuario usuario = usuarioCreate.toEntity();
        usuario= usuarioRepository.save(usuario);

        return UsuarioDto.toDto(usuario);
    }


    @Override
    @Transactional
    public UsuarioDto findById(Long Id) {
        //Se fija en la base de datos si ese id existe
        Usuario usuario = usuarioRepository.findById(Id).orElseThrow(()-> new NullPointerException("No se encontro Usuario "));
        if(!usuario.getActivo()){
            throw new IllegalArgumentException("Usuario no existe o eliminada de BD");
        }
        return UsuarioDto.toDto(usuario);
    }

    @Override
    @Transactional
    public List<UsuarioDto> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().filter(Usuario::getActivo).map(UsuarioDto::toDto).toList();
    }

    @Override
    @Transactional
    public UsuarioDto update(UsuarioEdit usuarioEdit, Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(()-> new NullPointerException("No se encontro Usuario "));
        if(Objects.equals(usuario.getMail(), usuarioEdit.mail())){
        }
        else if  (usuarioRepository.existsByMail(usuarioEdit.mail())){
            throw new IllegalArgumentException("Ya existe un email con dirección " + usuarioEdit.mail());
        }
        usuarioEdit.applyTo(usuario);
        usuario=usuarioRepository.save(usuario);

        return UsuarioDto.toDto(usuario);
    }

    @Override
    public void deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro Usuario con id  " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);

    }

    @Override
    @Transactional
    public UsuarioDto findByMail(String mail) {
        Usuario usuario = usuarioRepository.findByMail(mail).orElseThrow(()-> new NullPointerException("No se encontro Usuario con mail  " + mail));
        if (usuario.getActivo()==false){
            throw new IllegalArgumentException("Usuario desactivado");

        }
        return UsuarioDto.toDto(usuario);
    }

}
