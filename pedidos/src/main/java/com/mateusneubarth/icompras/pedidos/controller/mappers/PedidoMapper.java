package com.mateusneubarth.icompras.pedidos.controller.mappers;

import com.mateusneubarth.icompras.pedidos.controller.dto.ItemPedidoDTO;
import com.mateusneubarth.icompras.pedidos.controller.dto.NovoPedidoDTO;
import com.mateusneubarth.icompras.pedidos.model.ItemPedido;
import com.mateusneubarth.icompras.pedidos.model.Pedido;
import com.mateusneubarth.icompras.pedidos.model.enums.StatusPedido;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    ItemPedidoMapper ITEM_PEDIDO_MAPPER = Mappers.getMapper(ItemPedidoMapper.class);

    @Mapping(source = "itens", target = "itens", qualifiedByName = "mapItens")
    @Mapping(source = "dadosPagamento", target = "dadosPagamento")
    Pedido toEntity(NovoPedidoDTO dto);

    @Named("mapItens")
    default List<ItemPedido> mapItens(List<ItemPedidoDTO> dtos) {
        return dtos.stream().map(ITEM_PEDIDO_MAPPER::toEntity).toList();
    }

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido.PedidoBuilder pedido) {
        pedido.status(StatusPedido.REALIZADO);
        pedido.dataPedido(LocalDateTime.now());
    }
}