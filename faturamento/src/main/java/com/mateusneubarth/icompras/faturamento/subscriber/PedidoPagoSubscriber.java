package com.mateusneubarth.icompras.faturamento.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusneubarth.icompras.faturamento.mapper.PedidoMapper;
import com.mateusneubarth.icompras.faturamento.model.Pedido;
import com.mateusneubarth.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoPagoSubscriber {

    private final ObjectMapper objectMapper;
    private final GeradorNotaFiscalService geradorNotaFiscalService;
    private final PedidoMapper pedidoMapper;

    @KafkaListener(groupId = "icompras-faturamento",
            topics = "${icompras.config.kafka.topics.pedidos-pagos}")
    public void listen(String json) {
        try {
            log.info("Recebendo pedido para faturamento: {}", json);
            var representation = objectMapper.readValue(json, DetalhePedidoRepresentation.class);
            Pedido pedido = pedidoMapper.toPedido(representation);
            geradorNotaFiscalService.gerar(pedido);
        } catch (Exception e) {
            log.error("Erro na consumação do topico de pedidos pagos");
        }
    }
}
