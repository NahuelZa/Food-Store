package com.miempresa.tutorial.mi_primer_spring_boot.service;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaEdit;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoriaService {
    public CategoriaDto save (CategoriaCreate categoriaCreate);
    public CategoriaDto findById(Long Id);
    public List<CategoriaDto> findAll();
    public CategoriaDto update(CategoriaEdit categoriaEdit, Long idCategoria);
    public void deleteById(Long id);
}
