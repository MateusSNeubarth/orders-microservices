package com.mateusneubarth.icompras.faturamento.mapper;

import com.mateusneubarth.icompras.faturamento.model.Cliente;
import com.mateusneubarth.icompras.faturamento.model.ItemPedido;
import com.mateusneubarth.icompras.faturamento.model.Pedido;
import com.mateusneubarth.icompras.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import com.mateusneubarth.icompras.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoMapper {

    public Pedido toPedido(DetalhePedidoRepresentation detalhePedido) {
        Cliente cliente = Cliente.builder()
                .nome(detalhePedido.nome())
                .cpf(detalhePedido.cpf())
                .logradouro(detalhePedido.logradouro())
                .numero(detalhePedido.numero())
                .bairro(detalhePedido.bairro())
                .email(detalhePedido.email())
                .telefone(detalhePedido.telefone())
                .build();

        List<ItemPedido> itens = detalhePedido.itens().stream()
                .map(this::mapItem)
                .toList();

        return Pedido.builder()
                .codigo(detalhePedido.codigo())
                .cliente(cliente)
                .itens(itens)
                .data(detalhePedido.dataPedido())
                .total(detalhePedido.total())
                .build();
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation detalheItemPedido) {
        return new ItemPedido(
                detalheItemPedido.codigoProduto(),
                detalheItemPedido.nome(),
                detalheItemPedido.valorUnitario(),
                detalheItemPedido.quantidade(),
                detalheItemPedido.total()
        );
    }
}
