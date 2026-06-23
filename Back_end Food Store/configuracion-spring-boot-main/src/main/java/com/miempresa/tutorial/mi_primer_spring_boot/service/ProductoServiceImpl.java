package com.miempresa.tutorial.mi_primer_spring_boot.service;


import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoCreate;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoDto;
import com.miempresa.tutorial.mi_primer_spring_boot.dtos.producto.ProductoEdit;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Categoria;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.Producto;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.CategoriaRepository;
import com.miempresa.tutorial.mi_primer_spring_boot.entity.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{
    private  final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final CategoriaService categoriaService;


    @Override
    public ProductoDto save(ProductoCreate productoCreate) {
        //No se agrega el producto si el id de la  categoria no existe
        Categoria categoria = categoriaRepository.findById(productoCreate.idCategoria()).orElseThrow(() -> new NullPointerException("No se encontro categoria"));
        if(categoria.getEliminado()){
            throw new IllegalArgumentException("Categoria no existe o eliminada de BD");
        }
        //creo el producto y le agrego la categoria al final
        Producto producto = productoCreate.toEntity(categoria);
        //Te lo devuelve con el ID generado por la BD
        producto = productoRepository.save(producto);
        return ProductoDto.toDto(producto);
    }

    @Override
    public ProductoDto findById(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro Producto con ID " + id));
        if(!producto.getDisponible()){
            throw new IllegalArgumentException("Producto no existe o eliminada de BD");
        }

        return ProductoDto.toDto(producto);
    }

    @Override
    public List<ProductoDto> findAll() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().filter(Producto::getDisponible).map(ProductoDto::toDto).toList();
    }

    @Override
    //Buscamos el id del producto original y luego agregamos los cambios del edit
    public ProductoDto update(ProductoEdit productoEdit, Long idProducto) {
        Producto producto = productoRepository.findById(idProducto).orElseThrow(()-> new NullPointerException("No se encontro Porducto con Id " + idProducto));
        Categoria categoria = null;
        if(productoEdit.idCategoria()!=null){
            categoria = categoriaRepository.findById(productoEdit.idCategoria()).orElseThrow(() -> new NullPointerException("No se encontro categoria"));
            if(categoria.getEliminado()){
                System.out.println("Producto asignado a una categoria eliminada se le asignara categoria: null");
                categoria=null;
            }

        }
        productoEdit.applyTo(producto,categoria);
        producto=productoRepository.save(producto);

        return ProductoDto.toDto(producto);
    }

    @Override
    public void deleteById(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(()-> new NullPointerException("No se encontro Producto con ID " + id));
        producto.setDisponible(false);
        productoRepository.save(producto);

    }

    @Override
    public List<ProductoDto> findByCategoriaIdAndDisponible(Long categoriaId, Boolean disponible) {
        categoriaService.findById(categoriaId);
        List<Producto> productos= productoRepository.findByCategoriaIdAndDisponible(categoriaId,true);
        if(productos.isEmpty()){
            throw new NullPointerException("No se encontraron productos disponibles");
        }
        return productos.stream().map(ProductoDto::toDto).toList();
    }
}
