package com.mateusneubarth.icompras.faturamento.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemPedido(
        Long codigo,
        String descricao,
        BigDecimal valorUnitario,
        Integer quantidade
) {

    public BigDecimal getTotal() {
        return this.valorUnitario.multiply(BigDecimal.valueOf((this.quantidade)));
    }
}