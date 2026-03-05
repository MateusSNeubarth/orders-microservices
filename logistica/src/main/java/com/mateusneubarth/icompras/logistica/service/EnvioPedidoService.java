package com.mateusneubarth.icompras.logistica.service;

import com.mateusneubarth.icompras.logistica.model.AtualizacaoEnvioPedido;
import com.mateusneubarth.icompras.logistica.model.StatusPedido;
import com.mateusneubarth.icompras.logistica.publisher.EnvioPedidoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EnvioPedidoService {

    private final EnvioPedidoPublisher envioPedidoPublisher;

    public void enviar(Long codigo, String urlNotaFiscal) {
        var codigoRastreio = gerarCodigoRastreio();
        var atualizacaoRepresentation = new AtualizacaoEnvioPedido(codigo, StatusPedido.ENVIADO, codigoRastreio);
        envioPedidoPublisher.enviar(atualizacaoRepresentation);
    }

    private String gerarCodigoRastreio() {
        // AA123456789BR

        var random = new Random();

        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));

        int numeros = 100000000 + random.nextInt(900000000);

        return "" + letra1 + letra2 + numeros + "BR";
    }
}
