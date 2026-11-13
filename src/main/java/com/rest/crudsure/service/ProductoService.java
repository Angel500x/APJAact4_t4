package com.rest.crudsure.service;

import com.rest.crudsure.dto.ProductoRequest;
import com.rest.crudsure.dto.ProductoResponse;
import com.rest.crudsure.entity.Categoria;
import com.rest.crudsure.entity.Producto;
import com.rest.crudsure.exception.ResourceNotFoundException;
import com.rest.crudsure.repository.CategoriaRepository;
import com.rest.crudsure.repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<ProductoResponse> listarPaginado(Pageable pageable) {
        return productoRepository.findAll(pageable).map(this::mapToResponse);
    }

    public ProductoResponse obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return mapToResponse(producto);
    }

    public ProductoResponse crear(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + request.getCategoriaId()));

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);

        Producto guardado = productoRepository.save(producto);
        return mapToResponse(guardado);
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + request.getCategoriaId()));

        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);

        Producto actualizado = productoRepository.save(producto);
        return mapToResponse(actualizado);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    private ProductoResponse mapToResponse(Producto p) {
        return new ProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria().getId(),
                p.getCategoria().getNombre()
        );
    }
}
