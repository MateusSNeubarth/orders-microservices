package com.mateusneubarth.icompras.pedidos.controller.dto;

import com.mateusneubarth.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDTO(String dados, TipoPagamento tipoPagamento) {
}
