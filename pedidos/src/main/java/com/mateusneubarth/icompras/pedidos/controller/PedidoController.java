package com.mateusneubarth.icompras.pedidos.controller;

import com.mateusneubarth.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import com.mateusneubarth.icompras.pedidos.controller.dto.NovoPedidoDTO;
import com.mateusneubarth.icompras.pedidos.controller.mappers.PedidoMapper;
import com.mateusneubarth.icompras.pedidos.model.ErrorResponse;
import com.mateusneubarth.icompras.pedidos.model.Pedido;
import com.mateusneubarth.icompras.pedidos.model.exception.ItemNaoEcontradoException;
import com.mateusneubarth.icompras.pedidos.model.exception.ValidationException;
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
        try {
            var pedido = pedidoMapper.toEntity(dto);
            Pedido pedidoCadastrado = pedidoService.criarPedido(pedido);
            return ResponseEntity.ok(pedidoCadastrado.getCodigo());
        } catch (ValidationException e) {
            var errorResponse = new ErrorResponse("Erro Validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicaoNovoPagamentoDTO dto) {
        try {
            pedidoService.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEcontradoException e) {
            var errorResponse = new ErrorResponse("Item Não Encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
