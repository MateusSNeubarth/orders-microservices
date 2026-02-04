package com.mateusneubarth.icompras.pedidos.controller;

import com.mateusneubarth.icompras.pedidos.controller.dto.NovoPedidoDTO;
import com.mateusneubarth.icompras.pedidos.controller.mappers.PedidoMapper;
import com.mateusneubarth.icompras.pedidos.model.Pedido;
import com.mateusneubarth.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody NovoPedidoDTO dto) {
        var pedido = pedidoMapper.toEntity(dto);
        Pedido pedidoCadastrado = pedidoService.criarPedido(pedido);
        return ResponseEntity.ok(pedidoCadastrado.getCodigo());
    }
}
