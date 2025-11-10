package com.rafael.producto.exception;

public class CodigoProductoNoEncontradoException extends RuntimeException {
    public CodigoProductoNoEncontradoException(String codigo) {
        super("Producto con Código " + codigo + " no encontrado");
    }
}
