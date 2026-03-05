package com.mateusneubarth.icompras.pedidos.subscriber.representation;

import com.mateusneubarth.icompras.pedidos.model.enums.StatusPedido;

public record AtualizacaoStatusPedidoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal,
        String codigoRastreio
) {
}
