package com.mateusneubarth.icompras.pedidos.repository;

import com.mateusneubarth.icompras.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
