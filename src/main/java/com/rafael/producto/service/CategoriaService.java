package com.rafael.producto.service;

import com.rafael.producto.dto.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDTO> listarTodas();
    CategoriaDTO buscarPorId(Long id);
    CategoriaDTO guardar(CategoriaDTO dto);
    CategoriaDTO actualizar(Long id, CategoriaDTO dto);
    void eliminar(Long id);
}
