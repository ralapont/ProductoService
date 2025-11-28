package com.rafael.producto.service.impl;

import com.rafael.producto.dto.ProductoDTO;
import com.rafael.producto.exception.CodigoProductoNoEncontradoException;
import com.rafael.producto.exception.ProductoNoEncontradoException;
import com.rafael.producto.mapper.ProductoMapper;
import com.rafael.producto.model.entity.Producto;
import com.rafael.producto.model.respository.ProductoRepository;
import com.rafael.producto.service.ProductoServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @Override
    @Transactional
    public ProductoDTO actualizarStock(String codigo, Integer stock) {
        log.info("Actualizando stock del producto con código {}: {}", codigo, stock);
        productoRepository.actualizarStock(codigo, stock);
        Producto existente = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new CodigoProductoNoEncontradoException(codigo));
        return productoMapper.toDTO(existente);
    }

    @Override
    public ProductoDTO obtenerProductoCodigo(String codigo) {
        log.info("Obteniendo producto con código: {}", codigo);
        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new CodigoProductoNoEncontradoException(codigo));
        return productoMapper.toDTO(producto);
    }


}
