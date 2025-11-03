package com.rafael.producto.mapper;

import com.rafael.producto.dto.ProductoDTO;
import com.rafael.producto.model.entity.Categoria;
import com.rafael.producto.model.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(source = "categoria.id", target = "categoriaId")
    ProductoDTO toDTO(Producto producto);

    @Mapping(source = "categoriaId", target = "categoria.id")
    Producto toEntity(ProductoDTO dto);

    @Mapping(target = "id", ignore = true)
    void actualizarDesdeDTO(ProductoDTO dto, @MappingTarget Producto producto);

    default Categoria mapCategoria(Long id) {
        if (id == null) return null;
        return Categoria.builder().id(id).build();
    }
}
