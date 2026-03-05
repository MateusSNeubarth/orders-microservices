package com.mateusneubarth.icompras.faturamento.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusneubarth.icompras.faturamento.model.Pedido;
import com.mateusneubarth.icompras.faturamento.publisher.representation.AtualizacaoStatusPedido;
import com.mateusneubarth.icompras.faturamento.publisher.representation.StatusPedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${icompras.config.kafka.topics.pedidos-faturados}")
    private String topico;

    public void publicar(Pedido pedido, String urlNotaFiscal) {
        try {
            var representation = new AtualizacaoStatusPedido(pedido.codigo(), StatusPedido.FATURADO, urlNotaFiscal);

            String json = objectMapper.writeValueAsString(representation);

            kafkaTemplate.send(topico, json);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
