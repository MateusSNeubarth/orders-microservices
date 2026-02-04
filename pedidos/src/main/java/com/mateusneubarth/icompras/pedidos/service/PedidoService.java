package com.mateusneubarth.icompras.pedidos.service;

import com.mateusneubarth.icompras.pedidos.model.Pedido;
import com.mateusneubarth.icompras.pedidos.repository.ItemPedidoRepository;
import com.mateusneubarth.icompras.pedidos.repository.PedidoRepository;
import com.mateusneubarth.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator pedidoValidator;

    public Pedido criarPedido(Pedido pedido) {
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        pedido.setTotal(
                pedido.getItens().stream()
                        .map(item ->
                                item.getValorUnitario()
                                        .multiply(BigDecimal.valueOf(item.getQuantidade()))
                        )
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }
}
