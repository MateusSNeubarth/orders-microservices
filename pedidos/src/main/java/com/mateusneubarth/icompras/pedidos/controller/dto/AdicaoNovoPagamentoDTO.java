package com.mateusneubarth.icompras.pedidos.controller.dto;

import com.mateusneubarth.icompras.pedidos.model.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(Long codigoPedido, String dados, TipoPagamento tipoPagamento) {
}
