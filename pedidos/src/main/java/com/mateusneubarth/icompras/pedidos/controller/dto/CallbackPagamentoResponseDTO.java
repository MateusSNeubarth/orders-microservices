package com.mateusneubarth.icompras.pedidos.controller.dto;

public record CallbackPagamentoResponseDTO(
        Long codigo,
        String chavePagamento,
        Boolean status,
        String observacoes
) {
}
