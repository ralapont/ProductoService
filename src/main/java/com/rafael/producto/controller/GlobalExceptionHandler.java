package com.rafael.producto.controller;

import com.rafael.producto.exception.CategoriaNotFoundException;
import com.rafael.producto.exception.CodigoProductoNoEncontradoException;
import com.rafael.producto.exception.ProductoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarProductoNoEncontrado(ProductoNoEncontradoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Producto no encontrado");
        error.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CodigoProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarCodigoProductoNoEncontradoException(CodigoProductoNoEncontradoException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Producto no encontrado");
        error.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CategoriaNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarCategoriaNoEncontrada(CategoriaNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Categori no encontrada");
        error.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> manejarTipoIncorrecto(MethodArgumentTypeMismatchException ex) {
        String mensaje = "Valor inválido para el parámetro '" + ex.getName() + "': " + ex.getValue();
        return ResponseEntity.badRequest().body(mensaje);
    }
}
