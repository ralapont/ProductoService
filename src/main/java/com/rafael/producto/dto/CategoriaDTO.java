package com.rafael.producto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rafael.producto.model.entity.TipoIva;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoriaDTO {
    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @JsonProperty("nombre")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    @JsonProperty("descripcion")
    private String descripcion;

    @NotNull(message = "El IVA es obligatorio")
    @JsonProperty("iva")
    private TipoIva iva;

    private List<ProductoDTO> productos;
}
