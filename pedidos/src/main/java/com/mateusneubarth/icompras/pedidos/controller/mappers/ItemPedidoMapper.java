package com.mateusneubarth.icompras.pedidos.controller.mappers;

import com.mateusneubarth.icompras.pedidos.controller.dto.ItemPedidoDTO;
import com.mateusneubarth.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido toEntity(ItemPedidoDTO dto);
}
