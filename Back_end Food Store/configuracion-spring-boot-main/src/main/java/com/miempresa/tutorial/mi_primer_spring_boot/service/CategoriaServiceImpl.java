package com.miempresa.tutorial.mi_primer_spring_boot.service;

import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.categoria.CategoriaEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaDto save(CategoriaCreate categoriaCreate) {
        if (categoriaRepository.existsByNombre(categoriaCreate.nombre())) {
            throw new IllegalArgumentException("Ya existe una categoria con nombre " + categoriaCreate.nombre());
        }
        Categoria categoria = categoriaCreate.toEntity();
        categoria = categoriaRepository.save(categoria);
        return CategoriaDto.toDto(categoria);


    }

    @Override
    public CategoriaDto findById(Long Id) {
        //Se fija en la base de datos si ese id existe
        Categoria categoria = categoriaRepository.findById(Id).orElseThrow(()-> new NullPointerException("No se encontro Categoria "));
        if(categoria.getEliminado()){
            throw new IllegalArgumentException("No existe categoria o esta eliminada de la BD");
        }
        return CategoriaDto.toDto(categoria);
    }

    @Override
    public List<CategoriaDto> findAll() {
        List<Categoria> categorias= categoriaRepository.findAll();
        //Aplico a cada categoria encontrada la funcion toDto
        return categorias.stream().filter(categoria -> !categoria.getEliminado()).map(CategoriaDto::toDto).toList();
    }

    @Override
    public CategoriaDto update(CategoriaEdit categoriaEdit, Long idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow(()-> new NullPointerException("No se encontro idCategoria "));
        if(categoria.getEliminado()){
            throw new IllegalArgumentException("No existe categoria o esta eliminada de la BD");
        }
        //Se aplica a la categoria que se encontro los cambios de la categoria que se paso por parametro
        categoriaEdit.applyTo(categoria);
        categoria = categoriaRepository.save(categoria);
        return CategoriaDto.toDto(categoria);
    }

    @Override
    public void deleteById(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro idCategoria "));
        categoria.setEliminado(true);
        categoriaRepository.save(categoria);

    }
}
