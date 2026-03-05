package com.mateusneubarth.icompras.logistica.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusneubarth.icompras.logistica.service.EnvioPedidoService;
import com.mateusneubarth.icompras.logistica.subscriber.representation.AtualizacaoFaturamentoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoSubscriber {

    private final ObjectMapper objectMapper;
    private final EnvioPedidoService envioPedidoService;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = {
                    "${icompras.config.kafka.topics.pedidos-faturados}"
            }
    )
    public void listener(String json) {
        log.info("Recebendo pedido para envio: {}", json);

        try {
            var representation = objectMapper.readValue(json, AtualizacaoFaturamentoRepresentation.class);

            envioPedidoService.enviar(representation.codigo(), representation.urlNotaFiscal());

            log.info("Pedido processado com sucesso: {}", representation.codigo());
        } catch (Exception e) {
            log.error("Erro ao preparar pedido para envio: ", e);
        }
    }
}
