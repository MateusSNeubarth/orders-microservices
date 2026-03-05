package com.mateusneubarth.icompras.pedidos.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusneubarth.icompras.pedidos.service.AtualizacaoStatusPedidoService;
import com.mateusneubarth.icompras.pedidos.subscriber.representation.AtualizacaoStatusPedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoSubscriber {

    private final AtualizacaoStatusPedidoService atualizacaoStatusPedidoService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = {
                    "${icompras.config.kafka.topics.pedidos-faturados}",
                    "${icompras.config.kafka.topics.pedidos-enviados}"
            }
    )
    public void receberAtualizacao(String json) {
        log.info("Recebendo atualizacao: {}", json);

        try {
            var atualizacaoStatus = objectMapper.readValue(json, AtualizacaoStatusPedidoRepresentation.class);
            atualizacaoStatusPedidoService.atualizarStatus(
                    atualizacaoStatus.codigo(),
                    atualizacaoStatus.status(),
                    atualizacaoStatus.urlNotaFiscal(),
                    atualizacaoStatus.codigoRastreio()
            );

            log.info("Pedido atualizado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
