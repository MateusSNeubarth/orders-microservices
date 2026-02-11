package com.mateusneubarth.icompras.pedidos.model;

public record ErrorResponse(String message, String field, String error) {
}
