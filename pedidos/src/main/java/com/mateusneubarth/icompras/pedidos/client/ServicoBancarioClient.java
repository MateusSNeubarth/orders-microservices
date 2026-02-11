package com.mateusneubarth.icompras.pedidos.client;

import com.mateusneubarth.icompras.pedidos.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ServicoBancarioClient {

    public String solicitarPagamento(Pedido pedido) {
        log.info("Solicitanto pagamento para o pedido: {}", pedido.getCodigo());
        return UUID.randomUUID().toString();
    }
}
