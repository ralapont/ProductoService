package com.rafael.producto.service.impl;

import com.rafael.producto.dto.ProductoDTO;
import com.rafael.producto.exception.ProductoNoEncontradoException;
import com.rafael.producto.mapper.ProductoMapper;
import com.rafael.producto.model.entity.Producto;
import com.rafael.producto.model.respository.ProductoRepository;
import com.rafael.producto.service.ProductoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoServicio {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public List<ProductoDTO> listarTodos() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO crear(ProductoDTO dto) {
        Producto producto = productoMapper.toEntity(dto);
        Producto guardado = productoRepository.save(producto);
        return productoMapper.toDTO(guardado);
    }

    @Override
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
        return productoMapper.toDTO(producto);
    }

    @Override
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        productoMapper.actualizarDesdeDTO(dto, existente);

        Producto actualizado = productoRepository.save(existente);
        return productoMapper.toDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException(id);
        }
        productoRepository.deleteById(id);
    }


}
