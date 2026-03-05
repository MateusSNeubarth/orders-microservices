package com.mateusneubarth.icompras.pedidos.service;

import com.mateusneubarth.icompras.pedidos.model.enums.StatusPedido;
import com.mateusneubarth.icompras.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository pedidoRepository;

    @Transactional
    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String codigoRastreio) {
        pedidoRepository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(status);

            if (urlNotaFiscal != null) {
                pedido.setUrlNotaFiscal(urlNotaFiscal);
            }
            if (codigoRastreio != null) {
                pedido.setCodigoRastreio(codigoRastreio);
            }
        });
    }
}
