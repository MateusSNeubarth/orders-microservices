package com.mateusneubarth.icompras.pedidos.model.exception;

public class ItemNaoEcontradoException extends RuntimeException {
    public ItemNaoEcontradoException(String message) {
        super(message);
    }
}
