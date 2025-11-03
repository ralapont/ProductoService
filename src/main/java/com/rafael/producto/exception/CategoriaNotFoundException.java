package com.rafael.producto.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(Long id) {
        super("Categoría con ID " + id + " no encontrada.");
    }
}
