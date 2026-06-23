package com.miempresa.tutorial.mi_primer_spring_boot.service;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.usuario.UsuarioEdit;

import java.util.List;

public interface UsuarioService {
    public UsuarioDto save (UsuarioCreate usuarioCreate);
    public UsuarioDto findById(Long Id);
    public List<UsuarioDto> findAll();
    public UsuarioDto update(UsuarioEdit usuarioEdit, Long idUsuario);
    public void deleteById(Long id);
    public UsuarioDto findByMail(String mail);

}
