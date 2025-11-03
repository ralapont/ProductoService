package com.rafael.producto.mapper;

import com.rafael.producto.dto.CategoriaDTO;
import com.rafael.producto.model.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ProductoMapper.class)
public interface CategoriaMapper {
    CategoriaDTO toDTO(Categoria categoria);

    Categoria toEntity(CategoriaDTO dto);

    // Actualiza una entidad existente con los datos del DTO
    @Mapping(target = "id", ignore = true) // Evita sobrescribir el ID
    void actualizarDesdeDTO(CategoriaDTO dto, @MappingTarget Categoria categoria);
}
