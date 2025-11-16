package com.rafael.producto.controller;

import com.rafael.producto.dto.ProductoDTO;
import com.rafael.producto.service.ProductoServicio;
import jakarta.validation.Valid;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoServicio productoService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        List<ProductoDTO> productos = productoService.listarTodos();
        return ResponseEntity.ok(productos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductoDTO> crear(@Valid @RequestBody ProductoDTO dto) {
        ProductoDTO creado = productoService.crear(dto);
        URI location = URI.create("/api/productos/" + creado.getId());
        log.info("Producto creada: {}", creado);
        return ResponseEntity.created(location).body(creado);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtener(@PathVariable Long id) {
        ProductoDTO producto = productoService.obtenerPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        ProductoDTO actualizado = productoService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping( "/codigo")
    public ResponseEntity<ProductoDTO> obtenerProductoCodigo(
            @QueryParam("codigo") String codigo) {
        ProductoDTO producto = productoService.obtenerProductoCodigo(codigo);
        return ResponseEntity.ok(producto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{codigo}")
    public ResponseEntity<ProductoDTO> actualizarStock(
            @PathVariable String codigo,
            @QueryParam("stock") Integer stock) {
        ProductoDTO producto = productoService.actualizarStock(codigo, stock);
        return ResponseEntity.ok(producto);
    }
}
