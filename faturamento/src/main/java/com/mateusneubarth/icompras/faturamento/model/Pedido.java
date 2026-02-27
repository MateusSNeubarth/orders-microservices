package com.mateusneubarth.icompras.faturamento.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record Pedido(
        Long codigo,
        Cliente cliente,
        String data,
        BigDecimal total,
        List<ItemPedido> itens
) {
}
