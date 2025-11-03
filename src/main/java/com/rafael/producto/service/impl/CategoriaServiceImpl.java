package com.rafael.producto.service.impl;

import com.rafael.producto.dto.CategoriaDTO;
import com.rafael.producto.exception.CategoriaNotFoundException;
import com.rafael.producto.mapper.CategoriaMapper;
import com.rafael.producto.model.entity.Categoria;
import com.rafael.producto.model.respository.CategoriaRepository;
import com.rafael.producto.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDTO)
                .toList();
    }

    @Override
    public CategoriaDTO buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toDTO)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    @Override
    public CategoriaDTO guardar(CategoriaDTO dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        return categoriaMapper.toDTO(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaDTO actualizar(Long id, CategoriaDTO dto) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));

        categoriaMapper.actualizarDesdeDTO(dto, existente);

        Categoria actualizada = categoriaRepository.save(existente);
        return categoriaMapper.toDTO(actualizada);

    }

    @Override
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException(id);
        }
        categoriaRepository.deleteById(id);
    }
}
