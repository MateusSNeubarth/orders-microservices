package com.mateusneubarth.icompras.logistica.subscriber.representation;

import com.mateusneubarth.icompras.logistica.model.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
