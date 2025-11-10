package com.rafael.producto.service;

import com.rafael.producto.dto.ProductoDTO;

import java.util.List;

public interface ProductoServicio {
    List<ProductoDTO> listarTodos();
    ProductoDTO crear(ProductoDTO dto);
    ProductoDTO obtenerPorId(Long id);
    ProductoDTO actualizar(Long id, ProductoDTO dto);
    void eliminar(Long id);
    ProductoDTO actualizarStock(String codigo, Integer stock);
}
